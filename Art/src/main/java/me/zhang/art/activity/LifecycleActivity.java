package me.zhang.art.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.zhang.art.R;

/**
 * Created by Li on 6/5/2016 1:51 PM.
 */
public class LifecycleActivity extends AppCompatActivity {

    private static final String TAG = LifecycleActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(@Nullable Bundle savedInstanceState)");

        //noinspection ConstantConditions
        getSupportActionBar().setTitle("LifecycleActivity");

        setContentView(R.layout.activity_lifecycle);

        Button btnOpenNormalActivity = (Button) findViewById(R.id.btn_open_normal_activity);
        if (btnOpenNormalActivity != null) {
            btnOpenNormalActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LifecycleActivity.this, AnotherActivity.class));
                }
            });
        }

        Button btnOpenDialogActivity = (Button) findViewById(R.id.btn_open_dialog_activity);
        if (btnOpenDialogActivity != null) {
            btnOpenDialogActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LifecycleActivity.this, DialogActivity.class));
                }
            });
        }
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

}
