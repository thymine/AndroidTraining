package me.zhang.laboratory;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import me.zhang.library.ExampleLibrary;

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

    @Override
    public void onCreate() {
        super.onCreate();
        ExampleLibrary.Companion.callLibrary();
        System.out.println(Constants.TAG);
        Toast.makeText(sContext, Constants.Companion.getString(this), Toast.LENGTH_SHORT).show();
    }

}
