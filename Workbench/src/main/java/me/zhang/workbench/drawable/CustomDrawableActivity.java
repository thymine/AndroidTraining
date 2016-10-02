package me.zhang.workbench.drawable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import me.zhang.workbench.R;

/**
 * Created by Li on 2016/10/2 16:59.
 */

public class CustomDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_drawable);

        ImageView customImage = (ImageView) findViewById(R.id.customImage);
        customImage.setImageDrawable(new CustomDrawable(Color.BLUE));
    }
}
