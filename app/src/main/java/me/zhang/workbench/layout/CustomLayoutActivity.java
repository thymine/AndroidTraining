package me.zhang.workbench.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import me.zhang.workbench.R;

/**
 * Created by Zhang on 2016/7/5 上午 10:18 .
 */
public class CustomLayoutActivity extends AppCompatActivity {

    private static final String TAG = CustomLayoutActivity.class.getSimpleName();
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout);

        button = (Button) findViewById(R.id.button);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            Log.i(TAG, "onWindowFocusChanged: button top = " + button.getTop());
        }
    }
}
