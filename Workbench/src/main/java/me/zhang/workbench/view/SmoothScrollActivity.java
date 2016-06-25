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
    private SmoothView scrollerSmoothView;
    private SmoothView animationSmoothView;

    private int startY = 0;
    private int deltaY = DEST_Y;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    scrollerSmoothView.smoothScrollTo(0, 0);
                    break;
                case -1:
                    scrollerSmoothView.smoothScrollTo(0, DEST_Y);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smooth_scroll);

        //region Use Scroller
        scrollerSmoothView = (SmoothView) findViewById(R.id.scrollerSmoothView);
        assert scrollerSmoothView != null;

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
        animationSmoothView = (SmoothView) findViewById(R.id.animationSmoothView);
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        valueAnimator.setRepeatCount(INFINITE);
        valueAnimator.setRepeatMode(REVERSE);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = valueAnimator.getAnimatedFraction();
                Log.i(TAG, "onAnimationUpdate: fraction = " + fraction);
                animationSmoothView.scrollTo(0, (int) (deltaY * fraction) + startY);
            }
        });
        valueAnimator.start();
        //endregion
    }

}
