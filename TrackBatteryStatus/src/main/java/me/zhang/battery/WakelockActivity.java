package me.zhang.battery;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Zhang on 2015/6/19 下午 1:26 .
 */
public class WakelockActivity extends Activity {

    TextView mWakeLockMsg;
    ComponentName mServiceComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakelock);

        mWakeLockMsg = (TextView) findViewById(R.id.wakelock_txt);
        mServiceComponent = new ComponentName(this, MyJobService.class);
        Intent startServiceIntent = new Intent(this, MyJobService.class);
        startService(startServiceIntent);

        Button theButtonThatWakelocks = (Button) findViewById(R.id.wakelock_poll);
        theButtonThatWakelocks.setText(R.string.poll_server_button);

        theButtonThatWakelocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    pollServer();
                }
            }
        });
    }

    /**
     * This method polls the server via the JobScheduler API. By scheduling the job with this API,
     * your app can be confident it will execute, but without the need for a wake lock. Rather, the
     * API will take your network jobs and execute them in batch to best take advantage of the
     * initial network connection cost.
     * <p/>
     * The JobScheduler API works through a background service. In this sample, we have
     * a simple service in MyJobService to get you started. The job is scheduled here in
     * the activity, but the job itself is executed in MyJobService in the startJob() method. For
     * example, to poll your server, you would create the network connection, send your GET
     * request, and then process the response all in MyJobService. This allows the JobScheduler API
     * to invoke your logic without needed to restart your activity.
     * <p/>
     * For brevity in the sample, we are scheduling the same job several times in quick succession,
     * but again, try to consider similar tasks occurring over time in your application that can
     * afford to wait and may benefit from batching.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void pollServer() {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        for (int i = 0; i < 10; i++) {
            JobInfo jobInfo = new JobInfo.Builder(i, mServiceComponent)
                    .setMinimumLatency(5000) // 5 seconds
                    .setOverrideDeadline(60000) // 60 seconds (for brevity in the sample)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // WiFi or data connections
                    .build();

            mWakeLockMsg.append("Scheduling job " + i + "!\n");
            scheduler.schedule(jobInfo);
        }
    }

}
