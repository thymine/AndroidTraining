package me.zhang.workbench.design.animation;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.workbench.R;

public class AnimateActivity extends AppCompatActivity {

    @InjectView(R.id.animXmlButton)
    Button mXmlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        ButterKnife.inject(this);

        mXmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animationSet = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slideoutbutton);
                animationSet.setAnimationListener(new AnimationListenerStub() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mXmlButton.setVisibility(View.GONE);
                        startNewActivity();
                    }
                });
                mXmlButton.startAnimation(animationSet);
            }
        });

    }

    public void animCode(final View view) {
        AnimationSet animationSet = new AnimationSet(this, null);
        animationSet.addAnimation(new AlphaAnimation(1f, 0f));
        animationSet.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        animationSet.addAnimation(new TranslateAnimation(0, 0, 0, -view.getHeight()));
        animationSet.setAnimationListener(new AnimationListenerStub() {
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                startNewActivity();
            }
        });
        view.startAnimation(animationSet);
    }

    public void animProp(final View view) {
        view.animate()
                .alpha(0f)
                .translationY(-view.getHeight())
                .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                        startNewActivity();
                    }
                });

        ObjectAnimator.ofObject(
                view, // object we're animating
                "textColor", // property to animate
                new ArgbEvaluator(), // interpolation function
                Color.BLACK, // start value
                Color.RED // end value
        )
                .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();
    }

    private void startNewActivity() {
        startActivity(new Intent(AnimateActivity.this, TargetActivity.class));
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    public void slide(View view) {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.TOP);

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        TransitionManager.beginDelayedTransition(root, slide);
        view.setVisibility(View.INVISIBLE);
    }

}
