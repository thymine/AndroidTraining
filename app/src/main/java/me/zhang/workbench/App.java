package me.zhang.workbench;

import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO;

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

    public static Context getContext() {
        return sContext;

    }

}
