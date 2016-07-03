package me.zhang.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

import me.zhang.workbench.R;

/**
 * Created by Li on 7/3/2016 2:55 PM.
 */
public class GetViewDimensActivity extends AppCompatActivity {

    private MyTextView mMyTextView;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_view_dimens);

        mMyTextView = (MyTextView) findViewById(R.id.myTextView);
        mTextView = (TextView) findViewById(R.id.textView);

        // get MyTextView dimens here ?
        int width = mMyTextView.getWidth();
        int height = mMyTextView.getHeight();

        mTextView.setText(String.format(Locale.getDefault(), "Width: %s, Height: %s", width, height));
    }
}
