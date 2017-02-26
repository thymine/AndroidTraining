package me.zhang.workbench.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ThreadLocalActivity extends AppCompatActivity {

    private static final String TAG = ThreadLocalActivity.class.getSimpleName();
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

}
