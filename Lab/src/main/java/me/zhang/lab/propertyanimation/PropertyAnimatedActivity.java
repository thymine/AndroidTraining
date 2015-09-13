package me.zhang.lab.propertyanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.zhang.lab.R;

/**
 * Created by Zhang on 9/13/2015 4:19 下午.
 */
public class PropertyAnimatedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animated);

        animateWheel();

        animteSun();

        animateSky();

        animateCloud1();

        animateCloud2();
    }

    private void animateCloud2() {
        ImageView cloud2 = (ImageView) findViewById(R.id.cloud2);
        ObjectAnimator cloud2Anim = ObjectAnimator.ofFloat(
                cloud2,
                "x",
                -300
        );
        cloud2Anim.setDuration(getResources().getInteger(R.integer.animation_period));
        cloud2Anim.setRepeatCount(ValueAnimator.INFINITE);
        cloud2Anim.setRepeatMode(ValueAnimator.REVERSE);
        cloud2Anim.start();
    }

    private void animateCloud1() {
        // move the clouds
        ImageView cloud1 = (ImageView) findViewById(R.id.cloud1);
        ObjectAnimator cloud1Anim = ObjectAnimator.ofFloat(
                cloud1,
                "x",
                -350
        );
        cloud1Anim.setDuration(getResources().getInteger(R.integer.animation_period));
        cloud1Anim.setRepeatCount(ValueAnimator.INFINITE);
        cloud1Anim.setRepeatMode(ValueAnimator.REVERSE);
        cloud1Anim.start();
    }

    private void animateSky() {
        // darken the sky
        RelativeLayout sky = (RelativeLayout) findViewById(R.id.car_layout);
        ValueAnimator skyAnim = ObjectAnimator.ofInt(
                sky,
                "backgroundColor",
                Color.rgb(0x66, 0xcc, 0xff),
                Color.rgb(0x00, 0x66, 0x99)
        );
        skyAnim.setDuration(getResources().getInteger(R.integer.animation_period));
        skyAnim.setRepeatCount(ValueAnimator.INFINITE);
        skyAnim.setRepeatMode(ValueAnimator.REVERSE);
        skyAnim.setEvaluator(new ArgbEvaluator());
        skyAnim.start();
    }

    private void animteSun() {
        ImageView sun = (ImageView) findViewById(R.id.sun);
        AnimatorSet sunSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sun_swing);
        sunSet.setTarget(sun);
        sunSet.start();
    }

    private void animateWheel() {
        ImageView wheel = (ImageView) findViewById(R.id.wheel);
        AnimatorSet wheelSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.wheel_spin);
        wheelSet.setTarget(wheel);
        wheelSet.start();
    }

}
