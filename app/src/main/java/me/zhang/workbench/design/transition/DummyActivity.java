package me.zhang.workbench.design.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class DummyActivity extends AppCompatActivity {

    @BindView(R.id.detailScrollView)
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.addTarget(R.id.windowImage);
            slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
            slide.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
            getWindow().setEnterTransition(slide);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dummy);
        ButterKnife.bind(this);
    }

    @Override
    public void onEnterAnimationComplete() {
        final int startScrollPos = getResources().getDimensionPixelSize(R.dimen.window_height);
        Animator animator = ObjectAnimator.ofInt(
                mScrollView,
                "scrollY",
                startScrollPos
        ).setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
        animator.start();
    }
}
