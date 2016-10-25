package me.zhang.workbench.design.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import me.zhang.workbench.R;

public class AnimateActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        mButton = (Button) findViewById(R.id.animButton);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AnimationSet animationSet = new AnimationSet(this, null);
        animationSet.addAnimation(new AlphaAnimation(1f, 0f));
        animationSet.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        animationSet.addAnimation(new TranslateAnimation(0, 0, 0, -mButton.getHeight()));
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mButton.setVisibility(View.GONE);

                startActivity(new Intent(AnimateActivity.this, TargetActivity.class));
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mButton.startAnimation(animationSet);
    }
}
