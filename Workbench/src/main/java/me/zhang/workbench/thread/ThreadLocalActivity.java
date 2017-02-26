package me.zhang.workbench.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

public class ThreadLocalActivity extends AppCompatActivity {

    private static final String TAG = ThreadLocalActivity.class.getSimpleName();
    public static final int DELAY_MILLIS = 1000;
    private static final int UI = 9;
    public static final String THREAD_NAME = "THREAD_NAME";
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == UI) {
                Bundle data = msg.getData();
                String threadName = data.getString(THREAD_NAME);
                mLogText.setText(threadName);
                return true;
            }
            return false;
        }
    };
    private Handler mHandler = new Handler(mCallback); // Handler for UI
    private boolean mIsTick = true;

    @BindView(R.id.log)
    TextView mLogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_local);
        ButterKnife.bind(this);

        // Set from UI(Main) thread
        mBooleanThreadLocal.set(true);
        Log.i(TAG, Thread.currentThread().getName() + ": " + mBooleanThreadLocal.get()); // Print "true"

        workerThread();
    }

    private void workerThread() {
        new Thread("#Thread1") {
            @Override
            public void run() {
                super.run();
                mBooleanThreadLocal.set(false);
                Log.i(TAG, Thread.currentThread().getName() + ": " + mBooleanThreadLocal.get()); // Print "false"
            }
        }.start();

        new Thread("#Thread2") {
            @Override
            public void run() {
                super.run();
                Log.i(TAG, Thread.currentThread().getName() + ": " + mBooleanThreadLocal.get()); // Print "Null"
            }
        }.start();

        new Thread("#Thread3") {
            @Override
            public void run() {
                super.run();
                Bundle data = new Bundle();

                int tick = 0;
                while (mIsTick) {
                    SystemClock.sleep(DELAY_MILLIS);

                    data.putString(THREAD_NAME, Thread.currentThread().getName() + ", " + ++tick);
                    Message msg = Message.obtain();
                    msg.what = UI;
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        mIsTick = false;
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
