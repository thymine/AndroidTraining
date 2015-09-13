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

        animateSun();

        animateSky();

        animateCloud1();

        animateCloud2();

        animateHour();

        animateMinute();

        animateMoon();
    }

    private void animateMoon() {
        ImageView moon = (ImageView) findViewById(R.id.moon);
        AnimatorSet sunSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.moon_swing);
        sunSet.setTarget(moon);
        sunSet.start();
    }

    private void animateMinute() {
        ImageView minute = (ImageView) findViewById(R.id.clock_minute);
        AnimatorSet minuteSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.minute_spin);
        minuteSet.setTarget(minute);
        minuteSet.start();
    }

    private void animateHour() {
        ImageView hour = (ImageView) findViewById(R.id.clock_hour);
        AnimatorSet minuteSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.hour_spin);
        minuteSet.setTarget(hour);
        minuteSet.start();
    }

    private void animateCloud2() {
        ImageView cloud2 = (ImageView) findViewById(R.id.cloud2);
        ObjectAnimator cloud2Anim = ObjectAnimator.ofFloat(
                cloud2,
                "y",
                500
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
                "y",
                550
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

    private void animateSun() {
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
