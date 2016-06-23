package me.zhang.workbench.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Li on 6/23/2016 10:00 PM.
 */
public class StickyView extends TextView {

    private static final String TAG = StickyView.class.getSimpleName();
    private int mLastX;
    private int mLastY;

    public StickyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.i(TAG, "onTouchEvent: move, deltaX: " + deltaX + ", deltaY: " + deltaY);
                int translateX = (int) (getTranslationX() + deltaX);
                int translateY = (int) (getTranslationY() + deltaY);
                setTranslationX(translateX);
                setTranslationY(translateY);
                break;
            }

            case MotionEvent.ACTION_UP: {
                break;
            }

            default: {
                break;
            }
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
