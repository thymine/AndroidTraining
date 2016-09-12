package me.zhang.workbench.drawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import me.zhang.workbench.R;

/**
 * Created by Li on 9/12/2016 9:16 PM.
 */
public class LevelListActivity extends AppCompatActivity {

    private ImageView mWifiImage;
    private int mLevel = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_signal);

        mWifiImage = (ImageView) findViewById(R.id.wifiImage);
    }

    public void change(View view) {
        mWifiImage.setImageLevel(++mLevel % 5);
    }
}
