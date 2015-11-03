package me.zhang.lab.gesture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/11/3 下午 2:52 .
 */
public class TouchActivity extends Activity {
    private static final String DEBUG_TAG = "MainActivity";

    // This example shows an Activity, but you would use the same approach if
    // you were subclassing a View.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_touch);

        View touchView = findViewById(R.id.ll_touch);

        /**
         * Beware of creating a listener that returns false for the ACTION_DOWN event.
         * If you do this, the listener will not be called for the subsequent ACTION_MOVE and ACTION_UP string of events.
         * This is because ACTION_DOWN is the starting point for all touch events.
         */
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(DEBUG_TAG, "Touched");

                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "Action was DOWN");
                        break;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "Action was MOVE");
                        break;
                    case (MotionEvent.ACTION_UP):
                        Log.d(DEBUG_TAG, "Action was UP");
                        break;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "Action was CANCEL");
                        break;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                                "of current screen element");
                        break;
                    default:
                        break;
                }

                return true; // true: consumed, false: not
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}