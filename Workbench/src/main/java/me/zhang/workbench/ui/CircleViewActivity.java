package me.zhang.workbench.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import me.zhang.workbench.R;

import static android.view.View.FOCUS_DOWN;

/**
 * Created by Li on 7/5/2016 8:54 PM.
 */
public class CircleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        ((ScrollView) findViewById(R.id.scrollView)).fullScroll(FOCUS_DOWN);
    }

}
