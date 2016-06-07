package me.zhang.art.activity.flags;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/7/2016 8:44 PM.
 */
public class FlagsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("FlagsActivity");

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button launchClearTopButton = new Button(this);
        launchClearTopButton.setText("Launch ClearTopActivity");
        launchClearTopButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        launchClearTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlagsActivity.this, ClearTopActivity.class));
            }
        });

        Button launchExcludeFromRecentsButton = new Button(this);
        launchExcludeFromRecentsButton.setText("Launch ExcludeFromRecentsActivity");
        launchExcludeFromRecentsButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        launchExcludeFromRecentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlagsActivity.this, ExcludeFromRecentsActivity.class).setFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
            }
        });

        container.addView(launchClearTopButton);
        container.addView(launchExcludeFromRecentsButton);

        setContentView(container);
    }
}
