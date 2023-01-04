package me.zhang.laboratory;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Created by Li on 6/16/2016 9:37 PM.
 */
public class App extends Application {

    private static App sContext;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

}
