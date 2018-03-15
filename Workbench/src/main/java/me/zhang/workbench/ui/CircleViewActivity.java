package me.zhang.workbench.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

/**
 * Created by Li on 7/5/2016 8:54 PM.
 */
public class CircleViewActivity extends AppCompatActivity {

    private CircleMovingView circleMovingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        circleMovingView = findViewById(R.id.circleMovingView);
        circleMovingView.start();
    }

    @Override
    protected void onDestroy() {
        circleMovingView.stop();
        super.onDestroy();
    }
}
