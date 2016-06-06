package me.zhang.art.activity.standard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/6/2016 8:36 PM.
 */
public class StandardActivity extends AppCompatActivity {

    private static final String TAG = StandardActivity.class.getSimpleName();
    private static final String PLUS = "plus";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(@Nullable Bundle savedInstanceState)");

        String plus = getIntent().getStringExtra(PLUS);
        setTitle("+" + (plus == null ? "" : plus));

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button normalButton = new Button(this);
        normalButton.setText("Open new activity");
        normalButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the same activity repeatly
                startActivity(new Intent(StandardActivity.this, StandardActivity.class).putExtra(PLUS, getTitle()));
            }
        });

        Button newTaskButton = new Button(this);
        newTaskButton.setText("Open activity inside new task");
        newTaskButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
                getApplicationContext()
                        .startActivity(
                                new Intent(
                                        StandardActivity.this,
                                        StandardActivity.class
                                )
                                        .putExtra(PLUS, getTitle())
                                .setFlags(FLAG_ACTIVITY_NEW_TASK)
                        );
            }
        });

        container.addView(normalButton);
        container.addView(newTaskButton);

        setContentView(container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG, "onNewIntent(Intent intent)");
    }
}
