package me.zhang.workbench.design.image;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class RoundedImagesActivity extends AppCompatActivity {

    @BindView(R.id.roundedImage)
    ImageView mRoundedImage;

    @BindView(R.id.shapeSwitcher)
    RadioGroup mShapeSwitcher;

    @BindView(R.id.cornerRadiusSeeker)
    SeekBar mCornerRadiusSeeker;

    private int mRoundedImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounded_images);

        ButterKnife.bind(this);

        final RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(
                getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.moroccan)
        );
        mRoundedImage.setImageDrawable(drawable);
        mRoundedImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mRoundedImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    //noinspection deprecation
                    mRoundedImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                mRoundedImageHeight = mRoundedImage.getHeight();
                mCornerRadiusSeeker.setMax(mRoundedImageHeight);
            }
        });

        mShapeSwitcher.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.defaultImage:
                        drawable.setCircular(false);
                        mCornerRadiusSeeker.setEnabled(false);
                        break;
                    case R.id.circularImage:
                        drawable.setCircular(true);
                        mCornerRadiusSeeker.setEnabled(false);
                        break;
                    case R.id.roundedCornerImage:
                        drawable.setCircular(true);
                        mCornerRadiusSeeker.setEnabled(true);
                        break;
                }
            }
        });

        mCornerRadiusSeeker.setEnabled(false);
        mCornerRadiusSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawable.setCornerRadius(progress); // progress, default, 0..100
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
