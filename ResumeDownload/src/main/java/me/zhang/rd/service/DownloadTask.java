package me.zhang.rd.service;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import me.zhang.rd.db.IThreadDAO;
import me.zhang.rd.db.ThreadDAO;
import me.zhang.rd.entity.FileInfo;
import me.zhang.rd.entity.ThreadInfo;

/**
 * Created by zhang on 15-6-14 下午3:34.
 * <p>
 * Download task
 */
public class DownloadTask {

    private Context mContext;
    private FileInfo mFileInfo;
    private IThreadDAO mDAO;
    private int mFinished;
    public boolean mIsPause;

    public DownloadTask(Context context, FileInfo info) {
        this.mContext = context;
        this.mFileInfo = info;
        mDAO = new ThreadDAO(context);
    }

    public void download() {
        // read last download progress from database
        List<ThreadInfo> infos = mDAO.getThreadInfos(mFileInfo.getUrl());
        ThreadInfo info;
        if (infos.size() == 0) {
            // initialize thread info
            info = new ThreadInfo(
                    0,
                    mFileInfo.getUrl(),
                    0,
                    mFileInfo.getLength(),
                    0
            );
        } else {
            info = infos.get(0);
        }
        // start download
        new DownloadThread(info).start();
    }

    /**
     * Downlaod thread
     */
    private class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;

        public DownloadThread(ThreadInfo info) {
            mThreadInfo = info;
        }

        @Override
        public void run() {
            // insert thread info into database
            if (!mDAO.exists(mThreadInfo.getUrl(), mThreadInfo.getId())) {
                mDAO.insertThreadInfo(mThreadInfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream in = null;
            try {
                // set download progress
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3 * 1000);
                conn.setRequestMethod("GET");
                // new start = last start + last progress
                long start = mThreadInfo.getStart() + mThreadInfo.getProgress();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
                // set file writing position
                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intentUpdate = new Intent(DownloadService.ACTION_UPDATE);
                Intent intentFinished = new Intent(DownloadService.ACTION_FINISHED);
                mFinished += mThreadInfo.getProgress();
                // start download
                if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    in = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
//                    long time = System.currentTimeMillis();
                    while ((len = in.read(buffer)) != -1) { // read datas
                        // write into file
                        raf.write(buffer, 0, len);
                        // send download progress
                        mFinished += len;
                        intentUpdate.putExtra("finished", (int) (mFinished * 100 / mFileInfo.getLength()));
//                        if (System.currentTimeMillis() - time > 500) {
//                            time = System.currentTimeMillis();
//                            mContext.sendBroadcast(intentUpdate);
//                        }
                        mContext.sendBroadcast(intentUpdate);
                        // save progress when stop pressed
                        if (mIsPause) {
                            mDAO.updateThreadInfo(
                                    mThreadInfo.getUrl(),
                                    mThreadInfo.getId(),
                                    mFinished
                            );
                            return;
                        }
                    }
                    // delete download thread information
                    mDAO.deleteThreadInfo(mThreadInfo.getUrl(), mThreadInfo.getId());
                    // send broadcast
                    mContext.sendBroadcast(intentFinished);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                try {
                    if (raf != null) {
                        raf.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
