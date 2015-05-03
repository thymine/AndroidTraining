package me.zhang.bmps.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.zhang.bmps.R;

/**
 * Created by zhang on 15-5-3 上午11:28.
 */
public class ImageCache {

    private static ImageCache MINSTANCE;
    private Context mContext;
    private Bitmap mPlaceHolderBitmap;
    private LruCache<String, Bitmap> mMemoryCache;
    private final Object mDiskCacheLock = new Object();
    private DiskLruCache mDiskLruCache;
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 50; // 10MB
    private static final String DISK_CACHE_SUBDIR = "details";

    private ImageCache(Context context) {
        mContext = context;
        initCache(context);
    }

    public synchronized static ImageCache newInstance(Context context) {
        if (MINSTANCE == null)
            MINSTANCE = new ImageCache(context);
        return MINSTANCE;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void initCache(Context context) {
        mPlaceHolderBitmap =
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return value.getByteCount() / 1024;
            }
        };

        // Initialize disk cache on background thread
        File cacheDir = getDiskCacheDir(mContext, DISK_CACHE_SUBDIR);
        if (!cacheDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cacheDir.mkdir();
        }
        new InitDiskCacheTask().execute(cacheDir);
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                try {
                    // DiskLruCache.open(File directory, int appVersion, int valueCount, long maxSize)
                    mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(mContext), 1, DISK_CACHE_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDiskCacheStarting = false; // Finished initialization
                mDiskCacheLock.notifyAll(); // Wake any waiting threads
            }
            return null;
        }
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //noinspection ConstantConditions
            File extFile = context.getExternalCacheDir();
            if (extFile != null) {
                cachePath = extFile.getPath();
            } else {
                cachePath = Environment.getExternalStorageDirectory().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public void loadBitmap(Uri uri, ImageView imageView) {
        final String imageKey = uri.toString();
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            if (cancelPotentialWork(uri, imageView)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(uri);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private Bitmap getBitmapFromMemCache(String key) {
        String hashKey = hashKeyForDisk(key);
        return mMemoryCache.get(hashKey);
    }

    public String hashKeyForDisk(String key) {
        String cachekey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cachekey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cachekey = String.valueOf(key.hashCode());
        }
        return cachekey;
    }

    private String bytesToHexString(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        for (byte aDigest : digest) {
            String hex = Integer.toHexString(0xFF & aDigest);
            if (hex.length() == 1) {
                builder.append('0');
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    private boolean cancelPotentialWork(Uri uri, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if (bitmapWorkerTask != null) {
            final Uri bitmapUri = bitmapWorkerTask.getUri();
            // If bitmapUri is not yet set or it differs from the new uri
            if (bitmapUri == null || bitmapUri != uri) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    // Retrieve the task associated with a particular ImageView
    private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    class BitmapWorkerTask extends AsyncTask<Uri, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;
        private Uri uri;
        private Bitmap bitmap;

        public Uri getUri() {
            return uri;
        }

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewWeakReference = new WeakReference<>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Uri... params) {
            uri = params[0];

            final String imageKey = uri.toString();
            // Check disk cache in background thread
            bitmap = getBitmapFromDiskCache(imageKey);
            if (bitmap == null) { // Not found in disk cache
                // Process as normal
                InputStream in = null;
                try {
                    in = mContext.getContentResolver().openInputStream(uri);
                    bitmap = BitmapUtils.decodeSampledBitmapFromInputStream(in, 480, 800);
                    // Add decoded bitmap to memory cache - LruCache
                    addBitmapToMemoryCache(uri.toString(), bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // Displaying bitmaps quickly
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addBitmapToDiskCache(imageKey, bitmap);
                }
            }).start();

            return bitmap;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        public void addBitmapToDiskCache(String uri, Bitmap bitmap) {

            String hashKey = hashKeyForDisk(uri);
            // Also add to disk cache
            synchronized (mDiskCacheLock) {
                try {
                    if (mDiskLruCache != null && mDiskLruCache.get(hashKey) == null) {
                        DiskLruCache.Editor editor = mDiskLruCache.edit(hashKey);
                        if (editor != null) {
                            OutputStream out = editor.newOutputStream(0);
                            if (BitmapUtils.copy(bitmapToInputStream(bitmap), out)) {
                                editor.commit();
                            } else {
                                editor.abort();
                            }
                        }
                        mDiskLruCache.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public InputStream bitmapToInputStream(Bitmap bm) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return new ByteArrayInputStream(baos.toByteArray());
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
            String hashKey = hashKeyForDisk(key);
            if (getBitmapFromMemCache(key) == null) {
                mMemoryCache.put(hashKey, bitmap);
            }
        }

        public Bitmap getBitmapFromDiskCache(String key) {
            Bitmap bitmap = null;
            String hashKey = hashKeyForDisk(key);
            synchronized (mDiskCacheLock) {
                // Wait while disk cache is started from background thread
                while (mDiskCacheStarting) {
                    try {
                        mDiskCacheLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (mDiskLruCache != null) {
                    DiskLruCache.Snapshot snapshot = null;
                    try {
                        snapshot = mDiskLruCache.get(hashKey);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (snapshot != null) {
                        InputStream in = null;
                        try {
                            // Get bitmap input stream
                            in = snapshot.getInputStream(0);
                            // Decode stream to bitmap
                            bitmap = BitmapFactory.decodeStream(in);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (in != null) {
                                    in.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            // Check if the task is cancelled
            if (isCancelled()) {
                bitmap = null;
            }

            if (bitmap != null) {
                final ImageView imageView = imageViewWeakReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                // Check if the current task matches the one associated with the ImageView
                if (this == bitmapWorkerTask) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskWeakReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskWeakReference = new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskWeakReference.get();
        }

    }

}
