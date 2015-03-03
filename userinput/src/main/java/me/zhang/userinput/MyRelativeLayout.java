package me.zhang.userinput;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Zhang on 3/3/2015
 */
public class MyRelativeLayout extends RelativeLayout {


    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("Zhang", "MyRelativeLayout.onInterceptTouchEvent");
//        return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("Zhang", "MyRelativeLayout.onTouchEvent");
//        return super.onTouchEvent(event);
        return true;
    }

}
