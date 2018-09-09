package me.zhang.workbench.drawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

/**
 * Created by Li on 2016/10/2 16:25.
 */

public class ScaleDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_drawable);

        // 等级为0则不显示ScaleDrawable
        findViewById(R.id.scaleImage).getBackground().setLevel(0);
    }
}
