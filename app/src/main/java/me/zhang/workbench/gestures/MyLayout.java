package me.zhang.workbench.gestures;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import timber.log.Timber;

/**
 * Created by Zhang on 7/20/2017 9:10 PM.
 */
public class MyLayout extends FrameLayout {

    public MyLayout(Context context) {
        this(context, null);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Timber.w("dispatchTouchEvent(%s)", ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Timber.w("onInterceptTouchEvent(%s)", ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Timber.w("onTouchEvent(%s)", event);
        return super.onTouchEvent(event);
    }
}
