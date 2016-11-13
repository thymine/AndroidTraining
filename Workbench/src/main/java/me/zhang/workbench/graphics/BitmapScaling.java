package me.zhang.workbench.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.workbench.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BitmapScaling extends AppCompatActivity {

    @InjectView(R.id.scaledImageContainer)
    LinearLayout mScaledImageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_scaling);
        ButterKnife.inject(this);

        for (int i = 2; i < 10; i++) {
            addScaledImageView(i);
        }
    }

    private void addScaledImageView(int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        Bitmap scaledBitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.photo3, options);

        ImageView scaledImageView = new ImageView(this);
        scaledImageView.setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        scaledImageView.setImageBitmap(scaledBitmap);

        mScaledImageContainer.addView(scaledImageView);
    }
}
