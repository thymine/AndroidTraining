package me.zhang.lab.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Zhang on 2015/12/30 上午 10:32 .
 */
public class TouchTv extends TextView {

    private String TAG = TouchTv.class.getSimpleName();

    public TouchTv(Context context) {
        this(context, null);
    }

    public TouchTv(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchTv(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### dispatchTouchEvent " + ev.getAction());
        boolean result = super.dispatchTouchEvent(ev);
        Log.d(TAG, "### dispatchTouchEvent result = " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### onTouchEvent " + ev.getAction());
        boolean result = super.onTouchEvent(ev);
        Log.i(TAG, "### onTouchEvent result = " + result);
        return result;
    }
}

