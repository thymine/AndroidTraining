package me.zhang.workbench.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Li on 6/26/2016 10:46 AM.
 */
public class MyView extends TextView {

    private static final String TAG = TouchEventActivity.TAG;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "View, dispatchTouchEvent: action = " + event.getAction());
        boolean dispatch = super.dispatchTouchEvent(event);
        Log.i(TAG, "\tView, dispatchTouchEvent: " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "View, onTouchEvent: action = " + event.getAction());
        boolean touch = event.getAction() == MotionEvent.ACTION_DOWN || super.onTouchEvent(event);
//        boolean touch = super.onTouchEvent(event);
        Log.i(TAG, "\tView, onTouchEvent: " + touch);
        return touch;
    }

}
