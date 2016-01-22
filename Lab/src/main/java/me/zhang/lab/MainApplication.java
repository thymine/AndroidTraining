package me.zhang.lab;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Zhang on 2016/1/22 下午 2:08 .
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
