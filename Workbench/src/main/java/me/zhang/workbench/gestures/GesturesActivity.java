package me.zhang.workbench.gestures;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import me.zhang.workbench.R;

public class GesturesActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = GesturesActivity.class.getSimpleName();
    private boolean touch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

        Button button = (Button) findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return touch;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "onClick(...)");
            }
        });
    }

    public void onTouchToggle(View view) {
        touch = !touch;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.d(DEBUG_TAG, "dispatchTouchEvent(...)");
//        int action = MotionEventCompat.getActionMasked(event);
//
//        switch (action) {
//            case (MotionEvent.ACTION_DOWN):
//                Log.d(DEBUG_TAG, "DOWN");
//                return true;
//            case (MotionEvent.ACTION_MOVE):
//                Log.d(DEBUG_TAG, "MOVE");
//                return true;
//            case (MotionEvent.ACTION_UP):
//                Log.d(DEBUG_TAG, "UP");
//                return true;
//            case (MotionEvent.ACTION_CANCEL):
//                Log.d(DEBUG_TAG, "CANCEL");
//                return true;
//            case (MotionEvent.ACTION_OUTSIDE):
//                Log.d(DEBUG_TAG, "Movement occurred outside bounds of current screen element");
//                return true;
//            default:
//                return super.onTouchEvent(event);
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onTouchEvent(...)");
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
