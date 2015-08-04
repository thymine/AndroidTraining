package com.soufun.lab;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Zhang on 2015/8/4 上午 10:42 .
 * <p/>
 * 正计时
 */
public class CountUpActivity extends Activity {

    private static final String MY_PREFS_NAME = "const_count-up";
    private static final String POST_TIME_KEY = "post_time";
    private static final long INTERVAL = 60 * 1000; // 1min

    TextView txtCountUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_count_up);

        txtCountUp = (TextView) findViewById(R.id.tv_count_up);

    }

    public void post(View view) {
        /* 保存当前点击的时间 */
        savePostTime();
    }

    private void savePostTime() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(POST_TIME_KEY, System.currentTimeMillis());
        editor.apply();

        Toast.makeText(getApplicationContext(), "post.", Toast.LENGTH_SHORT).show();
    }

    public void count(View view) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        long postTime = prefs.getLong(POST_TIME_KEY, 0);

        final long passedTime = System.currentTimeMillis() - postTime;
        final long remains = INTERVAL - passedTime;

        if (remains < 0) {
            Toast.makeText(getApplicationContext(), "Time Passed!", Toast.LENGTH_SHORT).show();
            return;
        }

        new CountDownTimer(remains, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long passed = remains - millisUntilFinished;
                long displayed = passed + passedTime;
                Log.i("Zhang", "displayed: " + displayed);
                txtCountUp.setText(
                        String.format(
                                "%02d : %02d",
                                displayed / 1000 / 60,
                                displayed / 1000 % 60
                        )
                );
            }

            @Override
            public void onFinish() {
                txtCountUp.setText("done!");
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }

        }.start();
    }
}
