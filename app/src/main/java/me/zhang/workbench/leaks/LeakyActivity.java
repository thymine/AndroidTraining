package me.zhang.workbench.leaks;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.zhang.workbench.R;

/**
 * Created by Li on 5/29/2016 4:16 PM.
 */
public class LeakyActivity extends AppCompatActivity {

    private static Drawable background; // Cause leaks

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView label = new TextView(this);
        label.setText("Leaks are bad");

        if (background == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                background = getResources().getDrawable(R.drawable.large_bitmap, null);
            } else {
                //noinspection deprecation
                background = getResources().getDrawable(R.drawable.large_bitmap);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            label.setBackground(background);
        } else {
            //noinspection deprecation
            label.setBackgroundDrawable(background);
        }

        setContentView(label);
    }
}
