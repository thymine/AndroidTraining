package me.zhang.bmps.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Zhang on 4/26/2015 4:43 下午.
 */
public class BitmapWorkerTask extends AsyncTask<Uri, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;
    private final Context mContext;
    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    public BitmapWorkerTask(Context context, ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewWeakReference = new WeakReference<>(imageView);
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Uri... params) {
        uri = params[0];
        return BitmapUtils.decodeSampledBitmapFromInputStream(mContext, uri, 100, 100);
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
            final BitmapWorkerTask bitmapWorkerTask =
                    BitmapUtils.getBitmapWorkerTask(imageView);
            // Check if the current task matches the one associated with the ImageView
            if (this == bitmapWorkerTask) {
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
