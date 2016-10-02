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
    private Animation mRotate3dAnimation;

    private ImageView mTargetImage;
    private ImageView mRotate3dImgae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
        mTargetImage = (ImageView) findViewById(R.id.animTargetImage);
        mRotate3dImgae = (ImageView) findViewById(R.id.rorate3dImage);

        mAnimations = AnimationUtils.loadAnimation(this, R.anim.animations);
        mRotate3dAnimation = new Rotate3dAnimation(0, 360, 0, 0, 0, true);
        mRotate3dAnimation.setDuration(1600);
        mRotate3dAnimation.setRepeatCount(Animation.INFINITE);
    }

    public void startAnimations(View view) {
        mTargetImage.startAnimation(mAnimations);
        mRotate3dImgae.startAnimation(mRotate3dAnimation);
        view.setEnabled(false);
    }
}
