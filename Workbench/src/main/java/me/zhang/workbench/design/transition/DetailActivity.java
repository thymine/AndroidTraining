package me.zhang.workbench.design.transition;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.animation.AnimationUtils;

import me.zhang.workbench.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar_Details);
        setContentView(R.layout.activity_detail);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet transitions = new TransitionSet();
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
            slide.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            transitions.addTransition(slide);

            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            transitions.addTransition(fade);

            getWindow().setEnterTransition(transitions);
        }

        final int clickedColor = getIntent().getIntExtra(getString(R.string.key_clicked_color), Color.WHITE);
        findViewById(R.id.v_clicked_color).setBackgroundColor(clickedColor);
    }
}
