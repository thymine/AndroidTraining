package me.zhang.lab.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zhang on 2015/12/30 上午 10:32 .
 */
public class TouchTv extends View {

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
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### onTouchEvent " + ev.getAction());
        return super.onTouchEvent(ev);
//        return false;
//        return true;
    }
}

