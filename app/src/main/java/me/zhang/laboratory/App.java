package me.zhang.laboratory;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

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

    @Override
    public void onCreate() {
        super.onCreate();
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater()) // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater()) // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater()) // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater()) // CardView v7 控件换肤初始化[可选]
                // .setSkinStatusBarColorEnable(false) // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false) // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    public static Context getContext() {
        return sContext;
    }

}
