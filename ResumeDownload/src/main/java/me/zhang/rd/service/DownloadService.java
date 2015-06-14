package me.zhang.rd.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import me.zhang.rd.entity.FileInfo;

/**
 * Created by zhang on 15-6-14 上午9:30.
 */
public class DownloadService extends Service {

    public static final String TAG = "DownloadService";
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Download";
    public static final int MSG_INIT = 0;

    public static final String INTENT_FILE_INFO = "fileInfo";
    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_FINISHED = "finished";

    private DownloadTask mTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* 获取Activity传递的参数 */
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(INTENT_FILE_INFO);
            Log.i(TAG, "Start /// " + fileInfo.toString());

            // Start init thread
            new InitThread(fileInfo).start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(INTENT_FILE_INFO);
            Log.i(TAG, "Stop /// " + fileInfo.toString());

            if (mTask != null) {
                // stop download task
                mTask.mIsPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i(TAG, "Init /// " + fileInfo.toString());

                    // start download
                    mTask = new DownloadTask(DownloadService.this, fileInfo);
                    mTask.download();
                    break;
            }
        }
    };

    /**
     * 下载初始化线程
     */
    private class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo fileInfo) {
            mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3 * 1000);
                conn.setRequestMethod("GET");
                long len = -1;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // 获取文件长度
                    len = conn.getContentLength();
                }
                if (len <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                // 在本地创建文件
                File file = new File(dir, mFileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                // 设置文件长度
                raf.setLength(len);

                mFileInfo.setLength(len);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
