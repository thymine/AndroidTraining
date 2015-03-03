package me.zhang.userinput;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Zhang on 3/3/2015
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("Zhang", "MyLinearLayout.onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("Zhang", "MyLinearLayout.onTouchEvent");
        return super.onTouchEvent(event);
    }

}
