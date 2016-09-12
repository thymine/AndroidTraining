package me.zhang.workbench.drawable;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import me.zhang.workbench.R;

/**
 * Created by Li on 9/12/2016 9:27 PM.
 */
public class TransitionActivity extends AppCompatActivity {

    private ImageView mCrossFadeImage;
    private boolean mReversed;
    private TransitionDrawable mDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transition);
        mCrossFadeImage = (ImageView) findViewById(R.id.crossFadeImage);
        mDrawable = (TransitionDrawable) mCrossFadeImage.getBackground();
    }

    public void fade(View view) {
        if (mReversed) {
            mDrawable.reverseTransition(1000);
            mReversed = false;
        } else {
            mDrawable.startTransition(1000);
            mReversed = true;
        }
    }
}
