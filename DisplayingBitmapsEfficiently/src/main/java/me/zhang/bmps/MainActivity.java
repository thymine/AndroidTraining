package me.zhang.bmps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import me.zhang.bmps.util.BitmapUtils;


public class MainActivity extends ActionBarActivity {

    private static final int PICK_IMAGE = 0x10;
    private ImageView mPickedImage;
    private ProgressBar mLoadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPickedImage = (ImageView) findViewById(R.id.picked_image);
        mLoadProgress = (ProgressBar) findViewById(R.id.load_progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        InputStream in = null;
        try {
            in = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new BitmapWorkerTask(imageView).execute(in);
    }

    class BitmapWorkerTask extends AsyncTask<InputStream, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mLoadProgress != null) {
                mLoadProgress.setVisibility(View.VISIBLE);
            }
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(InputStream... params) {
            return BitmapUtils.decodeSampledBitmapFromInputStream(params[0], 480, 800);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mLoadProgress != null) {
                mLoadProgress.setVisibility(View.GONE);
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
