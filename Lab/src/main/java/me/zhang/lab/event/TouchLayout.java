package me.zhang.lab.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import static me.zhang.lab.event.TouchEventActivity.TAG;

/**
 * Created by Zhang on 2015/12/30 上午 10:12 .
 */
public class TouchLayout extends FrameLayout {

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
        Log.d(TAG, "### viewgroup dispatchTouchEvent() " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "### viewgroup onInterceptTouchEvent() " + ev.getAction());
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            return false;
//        }
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) { // 拦截ACTION_MOVE事件
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
//        return false;
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "### viewgroup onTouchEvent() " + ev.getAction());
        return super.onTouchEvent(ev);
//        return true;
//        return false;
    }

}

