package me.zhang.workbench.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.zhang.workbench.R;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.REVERSE;

/**
 * Created by Li on 6/24/2016 9:53 PM.
 */
public class SmoothScrollActivity extends AppCompatActivity {

    private static final String TAG = SmoothScrollActivity.class.getSimpleName();
    public static final int DEST_Y = 400;
    public static final int MESSAGE_SCROLL_TO = 0x10000000;
    public static final int FRAME_COUNT = 60;
    public static final int DELAYED_TIME = 10;

    private SmoothView mScrollerSmoothView;

    private SmoothView mAnimationSmoothView;
    private int startY = 0;
    private int deltaY = DEST_Y;

    private SmoothView mHandlerSmoothView;
    private int mCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smooth_scroll);

        //region Use Scroller
        mScrollerSmoothView = (SmoothView) findViewById(R.id.scrollerSmoothView);
        assert mScrollerSmoothView != null;

        new Thread(new Runnable() {

            boolean direction = false;

            @Override
            public void run() {
                //noinspection InfiniteLoopStatement
                while (true) {
                    SystemClock.sleep(3000);

                    if (direction) {
                        mHandler.sendEmptyMessage(1);
                    } else {
                        mHandler.sendEmptyMessage(-1);
                    }
                    direction = !direction;
                }
            }

        }).start();
        //endregion

        //region Use Animator
        mAnimationSmoothView = (SmoothView) findViewById(R.id.animationSmoothView);
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        valueAnimator.setRepeatCount(INFINITE);
        valueAnimator.setRepeatMode(REVERSE);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = valueAnimator.getAnimatedFraction();
//                Log.i(TAG, "onAnimationUpdate: fraction = " + fraction);
                mAnimationSmoothView.scrollTo(0, (int) (deltaY * fraction) + startY);
            }
        });
        valueAnimator.start();
        //endregion

        //region Use Handler
        mHandlerSmoothView = (SmoothView) findViewById(R.id.handlerSmoothView);
        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
        //endregion
    }

    private Handler mHandler = new Handler(new Handler.Callback() {

        private boolean direction = false;

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mScrollerSmoothView.smoothScrollTo(0, 0);
                    break;
                case -1:
                    mScrollerSmoothView.smoothScrollTo(0, DEST_Y);
                    break;
                case MESSAGE_SCROLL_TO:
                    if (direction) {
                        mCount--;
                        if (mCount == 0) {
                            direction = !direction;
                        }
                    } else {
                        mCount++;
                        if (mCount == FRAME_COUNT) {
                            direction = !direction;
                        }
                    }
                    if (mCount < FRAME_COUNT) {
                        float fraction = (float) mCount / FRAME_COUNT;
                        Log.i(TAG, "handleMessage: fraction = " + fraction);

                        int scrollY = (int) (fraction * DEST_Y);
                        mHandlerSmoothView.scrollTo(0, scrollY);
                    }

                    mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
