package me.zhang.workbench.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.zhang.workbench.R;

/**
 * Created by Li on 6/24/2016 9:53 PM.
 */
public class SmoothScrollActivity extends AppCompatActivity {

    private SmoothView smoothView;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    smoothView.smoothScrollTo(0, 0);
                    break;
                case -1:
                    smoothView.smoothScrollTo(0, smoothView.getHeight());
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smooth_scroll);

        smoothView = (SmoothView) findViewById(R.id.smoothView);
        assert smoothView != null;

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
    }

}
