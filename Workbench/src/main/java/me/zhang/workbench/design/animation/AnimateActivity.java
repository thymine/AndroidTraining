package me.zhang.workbench.design.animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        Animation animationSet = AnimationUtils.loadAnimation(this, R.anim.slideoutbutton);
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

    @Override
    protected void onStop() {
        super.onStop();
        mButton.setVisibility(View.VISIBLE);
    }

}
