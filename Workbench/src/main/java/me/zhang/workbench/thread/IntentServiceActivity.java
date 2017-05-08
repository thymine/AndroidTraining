package me.zhang.workbench.thread;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;
import me.zhang.workbench.service.CountMsgsIntentService;

public class IntentServiceActivity extends AppCompatActivity {

    private static final String TAG = IntentServiceActivity.class.getSimpleName();
    public static final String INTENT_EXTRA = "INTENT_EXTRA";
    private static final int MY_PERMISSION_REQUEST_READ_SMS = 0x01;
    private int mStartOrder;

    @BindView(R.id.count_msgs_button)
    Button mCountMsgsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ButterKnife.bind(this);

        mCountMsgsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(IntentServiceActivity.this,
                        Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            IntentServiceActivity.this, Manifest.permission.READ_SMS)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        showExplanation();
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(IntentServiceActivity.this,
                                new String[]{Manifest.permission.READ_SMS},
                                MY_PERMISSION_REQUEST_READ_SMS);
                    }
                } else {
                    triggerIntentService("10010");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_SMS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // sms-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void showExplanation() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_intent_service),
                "Please Grant Permission First!", Snackbar.LENGTH_LONG);
        snackbar.setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    void triggerIntentService(String phone) {
        Intent intent = new Intent(this, CountMsgsIntentService.class);
        intent.putExtra(CountMsgsIntentService.NUMBER_KEY, phone);
        startService(intent);
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
