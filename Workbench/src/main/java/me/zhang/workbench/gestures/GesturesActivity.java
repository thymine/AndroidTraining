package me.zhang.workbench.gestures;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import me.zhang.workbench.R;
import timber.log.Timber;

public class GesturesActivity extends AppCompatActivity {

    private ValueAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures);

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
                Timber.w("onTouch(), %f, %f", rawX, rawY);
                final int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        button.setTranslationX(button.getTranslationX() + (rawX - mPreX));
                        button.setTranslationY(button.getTranslationY() + (rawY - mPreY));
                        break;
                }
                mPreX = rawX;
                mPreY = rawY;
                return false;
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Timber.w("dispatchTouchEvent(%s)", ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Timber.w("onTouchEvent(%s)", event);
        return super.onTouchEvent(event);
    }

}
