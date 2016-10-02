package me.zhang.workbench.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import me.zhang.workbench.R;

public class AnimationsActivity extends AppCompatActivity {

    private Animation mAnimations;
    private ImageView mTargetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
        mTargetImage = (ImageView) findViewById(R.id.animTargetImage);

        mAnimations = AnimationUtils.loadAnimation(this, R.anim.animations);
    }

    public void startAnimations(View view) {
        mTargetImage.startAnimation(mAnimations);
        view.setEnabled(false);
    }
}
