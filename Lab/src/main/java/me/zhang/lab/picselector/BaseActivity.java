package me.zhang.lab.picselector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Zhang on 2015/10/29 下午 2:47 .
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();
}
