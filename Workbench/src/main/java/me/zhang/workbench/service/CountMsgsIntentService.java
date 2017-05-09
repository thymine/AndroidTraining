package me.zhang.workbench.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import me.zhang.workbench.R;

import static me.zhang.workbench.thread.IntentServiceActivity.PENDING_RESULT;
import static me.zhang.workbench.thread.IntentServiceActivity.RESULT;
import static me.zhang.workbench.thread.IntentServiceActivity.RESULT_CODE;

/**
 * Created by zhangxiangdong on 2017/5/8.
 */
public class CountMsgsIntentService extends IntentService {

    public static final String NUMBER_KEY = "number";
    private static final String TAG = CountMsgsIntentService.class.getSimpleName();

    public CountMsgsIntentService() {
        super("CountMsgsIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;

        String phoneNumber = intent.getStringExtra(NUMBER_KEY);
        Cursor cursor = getMsgsFrom(phoneNumber);
        int numberOfMsgs = cursor.getCount();
        Log.i(TAG, "onHandleIntent: numberOfMsgs -> " + numberOfMsgs);

        PendingIntent reply = intent.getParcelableExtra(PENDING_RESULT);
        Intent result = new Intent();
        result.putExtra(RESULT, numberOfMsgs);
        try {
            reply.send(this, RESULT_CODE, result);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        notifyUser(phoneNumber, numberOfMsgs);

    }

    private void notifyUser(String phoneNumber, int msgsCount) {
        String msg = String.format("Found %d from the phone number %s", msgsCount, phoneNumber);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_sms_counter_not)
                        .setContentTitle("Inbox Counter")
                        .setContentText(msg);
        // Gets an instance of the NotificationManager service
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Sets an unique ID for this notification
        nm.notify(phoneNumber.hashCode(), builder.build());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Cursor getMsgsFrom(String phoneNumber) {
        String[] projection = {
                Telephony.Sms._ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
        };

        String whereClause = Telephony.Sms.ADDRESS + " = '" + phoneNumber + "'";
        Uri quri = Uri.parse("content://sms/inbox");

        return getContentResolver().query(quri, projection, whereClause, null, null);
    }
}
