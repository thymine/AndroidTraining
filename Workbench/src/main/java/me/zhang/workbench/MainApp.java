package me.zhang.workbench;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import timber.log.Timber;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_AUTO;

/**
 * Created by Li on 6/16/2016 9:37 PM.
 */
public class MainApp extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_AUTO);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
