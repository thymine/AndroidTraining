package me.zhang.rd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.zhang.rd.entity.FileInfo;
import me.zhang.rd.service.DownloadService;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_FILE_INFO = "fileInfo";
    public static final String FILE_URL =
            "http://101.36.79.71/data/upload/marketClient/HiMarket4.5.7_1432632754322.apk";
    public static final String FILE_NAME = "HiMarket4.5.7_1432632754322.apk";

    @InjectView(R.id.id_filename)
    TextView mFileName;
    @InjectView(R.id.id_percent)
    TextView mPercent;
    @InjectView(R.id.id_progress)
    ProgressBar mProgress;
    @InjectView(R.id.id_start)
    Button mStart;
    @InjectView(R.id.id_stop)
    Button mStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        // 创建文件信息对象
        final FileInfo fileInfo = new FileInfo(0, FILE_URL, FILE_NAME, 0, 0);
        mFileName.setText(fileInfo.getName());

        mStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, DownloadService.class);
                        intent.setAction(DownloadService.ACTION_START);
                        intent.putExtra(INTENT_FILE_INFO, fileInfo);
                        startService(intent);
                    }
                }
        );
        mStop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, DownloadService.class);
                        intent.setAction(DownloadService.ACTION_STOP);
                        intent.putExtra(INTENT_FILE_INFO, fileInfo);
                        startService(intent);
                    }
                }
        );
        // register broadcast receiver, do not forget to unregister
        IntentFilter filter = new IntentFilter(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    /**
     * use this reciver to update download progress
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadService.ACTION_UPDATE.equals(action)) {
                // update progress
                int finished = intent.getIntExtra("finished", 0);
                mProgress.setProgress(finished);
                mPercent.setText(finished + "/100");
            } else if (DownloadService.ACTION_FINISHED.equals(action)) {
                Toast.makeText(getApplicationContext(), "Downlaod Finished!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
