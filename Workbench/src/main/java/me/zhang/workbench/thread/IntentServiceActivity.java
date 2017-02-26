package me.zhang.workbench.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import me.zhang.workbench.R;

public class IntentServiceActivity extends AppCompatActivity {

    private static final String TAG = IntentServiceActivity.class.getSimpleName();
    public static final String INTENT_EXTRA = "INTENT_EXTRA";
    private int mStartOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.startIntentService:
                mStartOrder++;

                Intent serviceIntent = new Intent(this, LocalIntentServicie.class);
                serviceIntent.putExtra(INTENT_EXTRA, mStartOrder);
                startService(serviceIntent);
                if (view instanceof Button) {
                    ((Button) view).setText(String.format(Locale.getDefault(), "Start Intent Service %d", mStartOrder));
                }
                Log.i(TAG, "onButtonClick: " + mStartOrder);
                break;
        }
    }

    public static class LocalIntentServicie extends IntentService {

        public static final int MS = 1000;
        private Handler mHandler = new Handler();

        public LocalIntentServicie() {
            super(LocalIntentServicie.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            SystemClock.sleep(MS);

            final int startOrder = intent.getIntExtra(INTENT_EXTRA, -1);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Start Order " + startOrder, Toast.LENGTH_SHORT).show();
                }
            });
            Log.i(TAG, "onHandleIntent: " + startOrder);
        }
    }


}
