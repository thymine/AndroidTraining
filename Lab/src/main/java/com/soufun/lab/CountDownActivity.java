package com.soufun.lab;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;


public class CountDownActivity extends Activity {

    private static final String MY_PREFS_NAME = "const_count-down";
    private static final String CONST_KEY = "const_key";
    private static final long INTERVAL = 5 * 60 * 1000; // ms

    private TextView timeToWaitText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        timeToWaitText = (TextView) findViewById(R.id.time_to_wait);

        long remains = getRemains();
        Log.i("Zhang", "remains: " + remains);
        new CountDownTimer(remains, 1000) {

            public void onTick(long millisUntilFinished) {
                timeToWaitText.setText(
                        String.format(
                                "%02d : %02d",
                                millisUntilFinished / 1000 / 60,
                                millisUntilFinished / 1000 % 60
                        )
                );
            }

            public void onFinish() {
                timeToWaitText.setText("done!");
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);

                // Reset constant
                buildConst();
            }

        }.start();

    }

    private long getRemains() {
        // Retrieve constant from preference
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        long constant = prefs.getLong(CONST_KEY, 0);
        long interval = System.currentTimeMillis() - constant;
        if (constant == 0 || interval > INTERVAL)
            buildConst();
        return constant - System.currentTimeMillis();
    }

    private void buildConst() {
        // Setting constant in Preference
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        // constant = (第一次进入界面时，系统当前时间) + 5mins
        long constant = System.currentTimeMillis() + INTERVAL;
        Log.i("Zhang", "constant: " + constant);
        editor.putLong(CONST_KEY, constant);
        editor.apply();
    }

}
