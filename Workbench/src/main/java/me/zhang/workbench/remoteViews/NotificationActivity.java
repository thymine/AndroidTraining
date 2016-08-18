package me.zhang.workbench.remoteViews;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.zhang.workbench.R;

/**
 * Created by Li on 8/10/2016 9:59 PM.
 */
public class NotificationActivity extends AppCompatActivity {

    private static int sCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    /**
     * trigger notification
     */
    public void notify(View view) {
        /** Create a Notification Builder */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World! " + ++sCount);

        /** Define the Notification's Action */
        Intent resultIntent = new Intent(this, ResultActivity.class);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        /** Set the Notification's Click Behavior */
        mBuilder.setContentIntent(resultPendingIntent);

        /** Issue the Notification */
        // Sets an ID for the notification
        int mNotificationId = 0x01;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
