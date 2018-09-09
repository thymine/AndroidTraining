package me.zhang.workbench.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import me.zhang.workbench.R;

public class LayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        LinearLayout container = (LinearLayout) findViewById(R.id.ll_container);
        LayoutInflater inflater = LayoutInflater.from(getApplication());

        for (int i = 0; i < 10; i++) {
            View task = inflater.inflate(R.layout.layout_item, container, false);
            container.addView(task);
        }

    }
}
