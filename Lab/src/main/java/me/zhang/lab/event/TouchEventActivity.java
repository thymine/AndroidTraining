package me.zhang.lab.event;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/12/30 上午 10:34 .
 */
public class TouchEventActivity extends Activity {

    private static final String TAG = "TouchActivity";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.touch_event_intercept);

        View myView = findViewById(R.id.my_button);

        ValueAnimator colorAnim = ObjectAnimator.ofInt(myView,
                "backgroundColor", /* Red */
                0xFFFF8080, /* Blue */0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(myView, "scaleX",
                0.5f);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.start();

        Log.d("", "### Activiti中getWindow()获取的类型是 : " + this.getWindow());

        // state list
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{
                android.R.attr.state_enabled
        }, getResources().getDrawable(R.mipmap.ic_launcher));
        stateListDrawable.addState(new int[]{
                android.R.attr.state_pressed
        }, getResources().getDrawable(R.mipmap.ic_launcher));

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "### activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "## activity onTouchEvent " + event.getAction());
        return super.onTouchEvent(event);
    }

}
