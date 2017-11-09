package me.zhang.workbench;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import timber.log.Timber;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_AUTO;

/**
 * Created by Li on 6/16/2016 9:37 PM.
 */
public class App extends Application {

    private static App sContext;

    static {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_AUTO);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static Context getContext() {
        return sContext;

    }

}
