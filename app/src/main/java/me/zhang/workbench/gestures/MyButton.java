package me.zhang.workbench.gestures;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import timber.log.Timber;

/**
 * Created by Zhang on 2016/4/11 下午 6:00 .
 */
public class MyButton extends AppCompatButton {

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Timber.d("onTouchEvent() DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Timber.d("onTouchEvent() MOVE");
                break;
            case (MotionEvent.ACTION_UP):
                Timber.d("onTouchEvent() UP");
                break;
            case (MotionEvent.ACTION_CANCEL):
                Timber.d("onTouchEvent() CANCEL");
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                Timber.d("onTouchEvent() Movement occurred outside bounds of current screen element");
                break;
        }
        return false;
    }
}
