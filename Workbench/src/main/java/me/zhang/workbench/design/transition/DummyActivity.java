package me.zhang.workbench.design.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AnimationUtils;

import me.zhang.workbench.R;

public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.description);
        slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        slide.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));

        getWindow().setEnterTransition(slide);

        setContentView(R.layout.activity_dummy);
    }
}
