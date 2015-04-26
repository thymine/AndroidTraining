package me.zhang.bmps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

import me.zhang.bmps.util.BitmapUtils;

/**
 * Created by Zhang on 4/26/2015 11:12 上午.
 */
public class AlphaActivity extends BaseActivity {

    private static final int PICK_IMAGE = 0x10;
    private ImageView mPickedImage;
    private Context mContext;
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);
        mContext = getApplicationContext();
        mPickedImage = (ImageView) findViewById(R.id.picked_image);
        mLoadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
    }

    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pick Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                loadBitmap(uri, mPickedImage);
            }
        }
    }

    private void loadBitmap(Uri uri, ImageView imageView) {
        new BitmapWorkerTask(mContext, imageView).execute(uri);
    }

    class BitmapWorkerTask extends AsyncTask<Uri, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;
        private final Context mContext;

        public BitmapWorkerTask(Context context, ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewWeakReference = new WeakReference<>(imageView);
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mLoadingProgress != null) {
                mLoadingProgress.setVisibility(View.VISIBLE);
            }
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Uri... params) {
            return BitmapUtils.decodeSampledBitmapFromInputStream(mContext, params[0], 480, 800);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (mLoadingProgress != null) {
                mLoadingProgress.setVisibility(View.GONE);
            }

            if (bitmap != null) {
                final ImageView imageView = imageViewWeakReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
