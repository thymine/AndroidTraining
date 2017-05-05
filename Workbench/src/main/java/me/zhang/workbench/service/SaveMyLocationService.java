package me.zhang.workbench.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;

import java.util.LinkedList;
import java.util.Queue;

import me.zhang.workbench.R;

/**
 * Created by zhangxiangdong on 2017/5/5.
 */
public class SaveMyLocationService extends Service {

    public static final String LOCATION_KEY = "location_key";
    boolean mShouldStop = false;
    final Queue<String> mJobs = new LinkedList<>();

    Thread mWorkerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (!mShouldStop) {
                String location = takeLocation();
                if (location != null) {
                    saveLocation(location);
                }
            }
        }
    });

    private String takeLocation() {
        String location = null;
        synchronized (mJobs) {
            if (mJobs.isEmpty()) {
                try {
                    mJobs.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            location = mJobs.poll();
        }
        return location;
    }

    private void saveLocation(String location) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String newLocations = sharedPref.getString(LOCATION_KEY, "") + "," + location;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOCATION_KEY, newLocations);
        editor.apply();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkerThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String location = intent.getStringExtra(LOCATION_KEY);
        synchronized (mJobs) {
            mJobs.add(location);
            mJobs.notify();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        synchronized (mJobs) {
            mShouldStop = true;
            mJobs.notify();
        }
        super.onDestroy();
    }
}
