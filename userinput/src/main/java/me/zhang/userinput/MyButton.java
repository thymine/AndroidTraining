package me.zhang.userinput;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Zhang on 3/3/2015
 */
public class MyButton extends Button {

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("Zhang", "MyButton.onTouchEvent");
        return super.onTouchEvent(event);
    }

}
