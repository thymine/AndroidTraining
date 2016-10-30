package me.zhang.workbench.design.vector;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import me.zhang.workbench.R;

public class VectorDrawableActivity extends AppCompatActivity {
    private ImageView imageView;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    private boolean full = false;

    private ImageView tickCross;
    private AnimatedVectorDrawable tickToCross;
    private AnimatedVectorDrawable crossToTick;
    private boolean tick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        imageView = (ImageView) findViewById(R.id.image_view);
        emptyHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_empty);
        fillHeart = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_heart_fill);

        tickCross = (ImageView) findViewById(R.id.tick_cross);
        tickToCross = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_tick_to_cross);
        crossToTick = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_cross_to_tick);
    }

    public void animateHeart(View view) {
        AnimatedVectorDrawable drawable = full ? emptyHeart : fillHeart;
        imageView.setImageDrawable(drawable);
        drawable.start();
        full = !full;
    }

    public void animateTickCross(View view) {
        AnimatedVectorDrawable drawable = tick ? tickToCross : crossToTick;
        tickCross.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }

}
