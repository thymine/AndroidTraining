package me.zhang.lab.event;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Zhang on 2015/12/30 上午 10:12 .
 */
public class TouchLayout extends FrameLayout {

    private static final String TAG = TouchLayout.class.getSimpleName();

    public TouchLayout(Context context) {
        super(context);
    }

    public TouchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        // Always handle the case of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Do not intercept touch event, let the child handle it
            return false;
        }

        Log.i(TAG, "### onInterceptTouchEvent " + action);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### onTouchEvent " + ev.getAction());
        Log.i(TAG, "### is Clickable = " + isClickable());
        return super.onTouchEvent(ev);
//        return true;
    }

}

