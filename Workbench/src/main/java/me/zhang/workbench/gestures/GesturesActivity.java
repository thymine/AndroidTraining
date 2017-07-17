package me.zhang.workbench.gestures;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import me.zhang.workbench.R;
import timber.log.Timber;

public class GesturesActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private GestureDetectorCompat mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

        mGestureDetector = new GestureDetectorCompat(this, this);
        mGestureDetector.setOnDoubleTapListener(this);

        final View button = findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {

            float mPreX, mPreY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final float rawX = event.getRawX();
                final float rawY = event.getRawY();
                final int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        button.setTranslationX(button.getTranslationX() + (rawX - mPreX));
                        button.setTranslationY(button.getTranslationY() + (rawY - mPreY));
                        break;
                }
                mPreX = rawX;
                mPreY = rawY;
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Timber.d("onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Timber.d("onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Timber.d("onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Timber.d("onScroll: " + e1.toString() + e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Timber.d("onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Timber.d("onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Timber.d("onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Timber.d("onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Timber.d("onSingleTapConfirmed: " + event.toString());
        return true;
    }

}
