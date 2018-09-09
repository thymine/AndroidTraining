package me.zhang.workbench.drawable;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import me.zhang.workbench.R;

/**
 * Created by Li on 2016/10/2 16:41.
 */

public class ClipDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clip_drawable);

        ImageView clipVertialBottomImage = (ImageView) findViewById(R.id.clipVertialBottomImage);
        ClipDrawable clipVertialBottomDrawable = (ClipDrawable) clipVertialBottomImage.getDrawable();
        clipVertialBottomDrawable.setLevel(6000);

        ImageView clipHorizontalRightImage = (ImageView) findViewById(R.id.clipHorizontalRightImage);
        ClipDrawable clipHorizontalRightDrawable = (ClipDrawable) clipHorizontalRightImage.getDrawable();
        clipHorizontalRightDrawable.setLevel(3000);
    }
}
