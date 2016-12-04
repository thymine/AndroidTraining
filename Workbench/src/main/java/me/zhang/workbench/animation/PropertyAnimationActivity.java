package me.zhang.workbench.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class PropertyAnimationActivity extends AppCompatActivity {

    @BindView(R.id.demoView)
    View mDemoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        ButterKnife.bind(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            final ValueAnimator transAnimator = ObjectAnimator.ofFloat(mDemoView, "translationY",
                    -mDemoView.getHeight());
            transAnimator.setRepeatCount(ValueAnimator.INFINITE);
            transAnimator.setRepeatMode(ValueAnimator.REVERSE);
            transAnimator.start();

            ValueAnimator colorAnimator = ObjectAnimator.ofInt(mDemoView, "backgroundColor",
                /* Red */ 0xFFFF8080, /* Blue */ 0xFF8080FF);
            colorAnimator.setDuration(1000);
            colorAnimator.setEvaluator(new ArgbEvaluator());
            colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
            colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
            colorAnimator.start();

            final AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(mDemoView, "rotationX", 0, 360),
                    ObjectAnimator.ofFloat(mDemoView, "rotationY", 0, 180),
                    ObjectAnimator.ofFloat(mDemoView, "rotation", 0, -90),
                    ObjectAnimator.ofFloat(mDemoView, "scaleX", 1, 0.75f),
                    ObjectAnimator.ofFloat(mDemoView, "scaleY", 1, 1.25f),
                    ObjectAnimator.ofFloat(mDemoView, "alpha", 1, 0.25f, 1)
            );
            set.setDuration(5 * 1000).start();

            ValueAnimator rotateAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,
                    R.animator.rotate);
            rotateAnimator.setTarget(mDemoView);
            rotateAnimator.setDuration(1000);
            rotateAnimator.start();
        }
    }

}
