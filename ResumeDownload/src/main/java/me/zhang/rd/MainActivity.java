package me.zhang.rd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.zhang.rd.entity.FileInfo;
import me.zhang.rd.service.DownloadService;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_FILE_INFO = "fileInfo";
    public static final String FILE_URL = "http://10.0.22.88/files/1064000000DC83F4/dldir1.qq.com/qqfile/qq/QQ7.3/15034/QQ7.3.exe";
    public static final String FILE_NAME = "QQ7.3.exe";

    private TextView mFileName;
    private ProgressBar mProgress;
    private Button mStart, mStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFileName = (TextView) findViewById(R.id.id_filename);
        mProgress = (ProgressBar) findViewById(R.id.id_progress);
        mStart = (Button) findViewById(R.id.id_start);
        mStop = (Button) findViewById(R.id.id_stop);

        // 创建文件信息对象
        final FileInfo fileInfo = new FileInfo(0, FILE_URL, FILE_NAME, 0, 0);

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
    }

}
