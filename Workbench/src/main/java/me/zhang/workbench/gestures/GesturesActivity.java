package me.zhang.workbench.gestures;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import me.zhang.workbench.R;
import timber.log.Timber;

public class GesturesActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private GestureDetectorCompat mGestureDetector;
    private ValueAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

        mGestureDetector = new GestureDetectorCompat(this, this);
        mGestureDetector.setOnDoubleTapListener(this);

        final TextView autoScrollTextView = (TextView) findViewById(R.id.tv_auto_scroll);
        final Scroller scroller = new Scroller(this);
        autoScrollTextView.setScroller(scroller);
        autoScrollTextView.setOnClickListener(new View.OnClickListener() {

            int mDeltaY = 200;

            @Override
            public void onClick(View v) {
                if (autoScrollTextView.getScrollY() >= autoScrollTextView.getHeight()) {
                    mDeltaY = -mDeltaY;
                } else if (autoScrollTextView.getScrollY() <= 0) {
                    mDeltaY = Math.abs(mDeltaY);
                }

                scroller.startScroll(0, autoScrollTextView.getScrollY(), 0, mDeltaY, 400);
                autoScrollTextView.invalidate();
            }
        });

        final TextView autoScrollTextView1 = (TextView) findViewById(R.id.tv_auto_scroll1);
        mAnimator = ValueAnimator.ofFloat(0, 1).setDuration(400);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                Timber.i("fraction: %f", fraction);
                autoScrollTextView1.scrollTo(0, (int) (200 * fraction));
            }
        });
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.start();

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

        final TextView scrollingTextView = (TextView) findViewById(R.id.tv_scrolling);
        scrollingTextView.setOnTouchListener(new View.OnTouchListener() {

            float mPreY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final float y = event.getY();
                final int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        scrollingTextView.scrollBy(0, (int) (mPreY - y));
                        break;
                }
                mPreY = y;
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        super.onDestroy();
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
