package me.zhang.lab.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static me.zhang.lab.event.TouchEventActivity.TAG;

/**
 * Created by Zhang on 2015/12/30 上午 10:32 .
 */
public class TouchView extends View {

    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### view dispatchTouchEvent() " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### view onTouchEvent() " + ev.getAction());
        return super.onTouchEvent(ev);
//        return false;
//        return true;
    }
}

