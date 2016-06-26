package me.zhang.workbench.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by Li on 6/26/2016 10:45 AM.
 */
public class MyLayout extends RelativeLayout {

    private static final String TAG = TouchEventActivity.TAG;

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "Layout, dispatchTouchEvent: action = " + ev.getAction());
        boolean dispatch = super.dispatchTouchEvent(ev);
        Log.i(TAG, "\tLayout, dispatchTouchEvent: " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "Layout, onInterceptTouchEvent: action = " + ev.getAction());
        boolean intercept = ev.getAction() == ACTION_DOWN || super.onInterceptTouchEvent(ev);
        Log.i(TAG, "\tLayout, onInterceptTouchEvent: " + intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "Layout, onTouchEvent: action = " + event.getAction());
        boolean touch = super.onTouchEvent(event);
        Log.i(TAG, "\tLayout, onTouchEvent: " + touch);
        return touch;
    }

}
