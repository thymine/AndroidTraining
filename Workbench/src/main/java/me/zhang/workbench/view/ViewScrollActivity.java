package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;

import me.zhang.workbench.R;

/**
 * Created by Li on 6/22/2016 10:47 AM.
 */
public class ViewScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scroll);

        MyTextView scrollableTextView = (MyTextView) findViewById(R.id.scrollableTextView);
        if (scrollableTextView != null) {
            scrollableTextView.setMovementMethod(new ScrollingMovementMethod());
        }

    }
}
