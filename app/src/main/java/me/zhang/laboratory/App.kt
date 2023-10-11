package me.zhang.laboratory;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import me.zhang.laboratory.ui.net.NameFakeService;
import me.zhang.laboratory.ui.room.AppDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Li on 6/16/2016 9:37 PM.
 */
public class App extends Application {

    private static App sContext;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
    }

    private static AppDatabase roomDb;
    private static NameFakeService nameFakeService;

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
        roomDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "room_db").build();
        nameFakeService = new Retrofit.Builder().baseUrl("https://api.namefake.com/").addConverterFactory(GsonConverterFactory.create()).build().create(NameFakeService.class);
    }

    @NonNull
    public static AppDatabase getRoomDb() {
        return roomDb;
    }

    @NonNull
    public static NameFakeService getNameFakeService() {
        return nameFakeService;
    }
}
