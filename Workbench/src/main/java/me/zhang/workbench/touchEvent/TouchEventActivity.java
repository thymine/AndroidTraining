package me.zhang.workbench.touchEvent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import me.zhang.workbench.R;

/**
 * Created by Li on 6/26/2016 10:47 AM.
 */
public class TouchEventActivity extends AppCompatActivity {

    public static final String TAG = "TouchEventActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "Activity, dispatchTouchEvent: action = " + ev.getAction());
        boolean dispatch = super.dispatchTouchEvent(ev);
        Log.i(TAG, "\tActivity, dispatchTouchEvent: " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "Activity, onTouchEvent: action = " + event.getAction());
        boolean touch = super.onTouchEvent(event);
        Log.i(TAG, "\tActivity, onTouchEvent: " + touch);
        return touch;
    }
}
