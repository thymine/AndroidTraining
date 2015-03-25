package me.zhang.property_animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import me.zhang.androidtraining.R;

/**
 * Created by Zhang on 2015/3/25 9:29.
 */
public class PropertyAnimatedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animated);

        ImageView wheel = (ImageView) findViewById(R.id.wheel);

        AnimatorSet wheelSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.wheel_spin);
        wheelSet.setTarget(wheel);
        wheelSet.start();

        // get the sun view
        ImageView sun = (ImageView) findViewById(R.id.sun);
        // load the sun movement animation
        AnimatorSet sunSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sun_swing);
        // set the view as target
        sunSet.setTarget(sun);
        sunSet.start();

        ValueAnimator skyAnim = ObjectAnimator.ofInt(
                findViewById(R.id.car_layout),
                "backgroundColor",
                Color.rgb(0x66, 0xcc, 0xff),
                Color.rgb(0x00, 0x66, 0x99)
        );
        skyAnim.setDuration(3000);
        skyAnim.setRepeatCount(ValueAnimator.INFINITE);
        skyAnim.setRepeatCount(ValueAnimator.REVERSE);
        skyAnim.setEvaluator(new ArgbEvaluator());
        skyAnim.start();

        ObjectAnimator cloudAnim = ObjectAnimator.ofFloat(findViewById(R.id.cloud1), "x", -350);
        cloudAnim.setDuration(3000);
        cloudAnim.setRepeatCount(ValueAnimator.INFINITE);
        cloudAnim.setRepeatMode(ValueAnimator.REVERSE);
        cloudAnim.start();
        ObjectAnimator cloud2Anim = ObjectAnimator.ofFloat(findViewById(R.id.cloud2), "x", -300);
        cloud2Anim.setDuration(3000);
        cloud2Anim.setRepeatCount(ValueAnimator.INFINITE);
        cloud2Anim.setRepeatMode(ValueAnimator.REVERSE);
        cloud2Anim.start();
    }
}
