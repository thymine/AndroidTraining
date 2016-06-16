package me.zhang.workbench;

import android.app.Application;

/**
 * Created by Li on 6/16/2016 9:37 PM.
 */
public class MainApp extends Application {

    public static Application me;

    @Override
    public void onCreate() {
        super.onCreate();

        me = this;
    }

}
