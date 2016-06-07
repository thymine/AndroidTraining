package me.zhang.art.activity.flags;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/7/2016 9:01 PM.
 */
public class NActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("NActivity");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button launchClearTopButton = new Button(this);
        launchClearTopButton.setText("Launch ClearTopActivity With CLEAR_TOP");
        launchClearTopButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        launchClearTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NActivity.this, ClearTopActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button launchSingleTaskClearTopButton = new Button(this);
        launchSingleTaskClearTopButton.setText("Launch ClearTopActivity With CLEAR_TOP | SINGLE_TOP");
        launchSingleTaskClearTopButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        launchSingleTaskClearTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NActivity.this, ClearTopActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

        container.addView(launchClearTopButton);
        container.addView(launchSingleTaskClearTopButton);

        setContentView(container);
    }

}
