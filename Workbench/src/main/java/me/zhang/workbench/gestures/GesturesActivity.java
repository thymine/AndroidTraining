package me.zhang.workbench.gestures;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import me.zhang.workbench.R;
import timber.log.Timber;

public class GesturesActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = GesturesActivity.class.getSimpleName();
    private boolean touch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = MotionEventCompat.getActionMasked(event);
//                switch (action) {
//                    case (MotionEvent.ACTION_DOWN):
//                        Log.d(DEBUG_TAG, "onTouch() DOWN");
//                        return false;
//                    case (MotionEvent.ACTION_MOVE):
//                        Log.d(DEBUG_TAG, "onTouch() MOVE");
//                        break;
//                    case (MotionEvent.ACTION_UP):
//                        Log.d(DEBUG_TAG, "onTouch() UP");
//                        break;
//                    case (MotionEvent.ACTION_CANCEL):
//                        Log.d(DEBUG_TAG, "onTouch() CANCEL");
//                        break;
//                    case (MotionEvent.ACTION_OUTSIDE):
//                        Log.d(DEBUG_TAG, "onTouch() Movement occurred outside bounds of current screen element");
//                        break;
//                }
//                return touch;
//            }
//        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(GesturesActivity.this, "onClick()", Toast.LENGTH_SHORT).show();
//                Log.d(DEBUG_TAG, "onClick()");
//            }
//        });
//        button.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(GesturesActivity.this, "onLongClick()", Toast.LENGTH_SHORT).show();
//                Log.d(DEBUG_TAG, "onLongClick()");
//                return false;
//            }
//        });
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
//                Log.d(DEBUG_TAG, "dispatchTouchEvent() DOWN");
//                return true;
//            case (MotionEvent.ACTION_MOVE):
//                Log.d(DEBUG_TAG, "dispatchTouchEvent() MOVE");
//                return true;
//            case (MotionEvent.ACTION_UP):
//                Log.d(DEBUG_TAG, "dispatchTouchEvent() UP");
//                return true;
//            case (MotionEvent.ACTION_CANCEL):
//                Log.d(DEBUG_TAG, "dispatchTouchEvent() CANCEL");
//                return true;
//            case (MotionEvent.ACTION_OUTSIDE):
//                Log.d(DEBUG_TAG, "dispatchTouchEvent() Movement occurred outside bounds of current screen element");
//                return true;
//            default:
//                return super.onTouchEvent(event);
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Timber.d("Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Timber.d("Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Timber.d("Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Timber.d("Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Timber.d("Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

}
