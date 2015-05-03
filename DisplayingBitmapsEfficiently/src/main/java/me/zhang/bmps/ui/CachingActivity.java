package me.zhang.bmps.ui;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
import java.util.ArrayList;
import java.util.List;

import me.zhang.bmps.R;
import me.zhang.bmps.util.BitmapUtils;
import me.zhang.bmps.util.Utils;

/**
 * Created by Zhang on 2015/4/29 2:07 下午.
 */
public class CachingActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static List<Uri> mDatas;
    private Bitmap mPlaceHolderBitmap;
    private GridView mGridView;
    private Context mContext;

    private LruCache<String, Bitmap> mMemoryCache;

    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        mContext = getApplicationContext();

        CachingFragment cachingFragment = CachingFragment.findOrCreateCacheFragment(getSupportFragmentManager());
        mMemoryCache = cachingFragment.mRetainedCache;
        if (mMemoryCache == null) {
            /**
             * Note: In this example, one eighth of the application memory is allocated for our cache.
             * On a normal/hdpi device this is a minimum of around 4MB (32/8).
             * A full screen GridView filled with images on a device with 800x480 resolution
             * would use around 1.5MB (800*480*4 bytes),
             * so this would cache a minimum of around 2.5 pages of images in memory.
             */
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

            // And back to mRetainedCache
            cachingFragment.mRetainedCache = mMemoryCache;
        }

        // Initialize disk cache on background thread
        File cacheDir = getDiskCacheDir(mContext, DISK_CACHE_SUBDIR);
        if (!cacheDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cacheDir.mkdir();
        }
        new InitDiskCacheTask().execute(cacheDir);

        mPlaceHolderBitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        final int mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        final int mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mGridView = (GridView) findViewById(R.id.image_grid);

        List<String> images = getAllShownImagesPath(mContext);
        mDatas = new ArrayList<>();
        for (String s : images) {
            mDatas.add(Uri.fromFile(new File(s)));
        }

        final ImageGridAdapter adapter = new ImageGridAdapter(mDatas);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(this);

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Pause fetcher to ensure smoother scrolling when flinging
                // TODO
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int numColumns = (int) Math.floor(
                        mGridView.getWidth() / (mImageThumbSize + mImageThumbSpacing)
                );
                if (numColumns > 0) {
                    final int columnWidth =
                            (mGridView.getWidth() / numColumns) - mImageThumbSpacing;
                    //noinspection SuspiciousNameCombination
                    adapter.setItemHeight(columnWidth);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    //noinspection deprecation
                    mGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
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


    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
    // but if not mounted, falls back on internal storage.
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        final Intent i = new Intent(this, ImageDetailActivity.class);
        i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
        if (Utils.hasJellyBean()) {
            // makeThumbnailScaleUpAnimation() looks kind of ugly here as the loading spinner may
            // show plus the thumbnail image in GridView is cropped. so using
            // makeScaleUpAnimation() instead.
            ActivityOptions options =
                    ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        String hashKey = hashKeyForDisk(key);
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(hashKey, bitmap);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private Bitmap getBitmapFromMemCache(String key) {
        String hashKey = hashKeyForDisk(key);
        return mMemoryCache.get(hashKey);
    }

    /**
     * Getting All Images Path
     *
     * @return ArrayList with images Path
     */
    public List<String> getAllShownImagesPath(Context context) {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        List<String> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA};
        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }
        cursor.close();
        return listOfAllImages;
    }

    private void loadBitmap(Uri uri, ImageView imageView) {
        final String imageKey = uri.toString();
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            if (cancelPotentialWork(uri, imageView)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(getResources(), mPlaceHolderBitmap, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(uri);
            }
        }
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

    class ImageGridAdapter extends BaseAdapter {
        private final List<Uri> mDatas;
        private int mItemHeight = 0;
        private GridView.LayoutParams mImageViewLayoutParams;

        public ImageGridAdapter(List<Uri> datas) {
            mDatas = datas;
            mImageViewLayoutParams = new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            mImageViewLayoutParams =
                    new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(mImageViewLayoutParams);
            } else { // Otherwise re-use the converted view
                imageView = (ImageView) convertView;
            }

            // Check the height matches our calculated column width
            if (imageView.getLayoutParams().height != mItemHeight) {
                imageView.setLayoutParams(mImageViewLayoutParams);
            }

            loadBitmap(mDatas.get(position), imageView);
            return imageView;
        }
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
                    bitmap = BitmapUtils.decodeSampledBitmapFromInputStream(in, 120, 120);
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
                    // Add final bitmap to caches - take time task
                    addBitmapToDiskCache(imageKey, bitmap);
                }
            }).start();

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
