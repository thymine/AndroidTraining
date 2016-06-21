package me.zhang.workbench.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 2016/6/21 下午 4:43 .
 */
public class SimpleLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_layout);
    }
}
