package me.zhang.laboratory

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room.databaseBuilder
import me.zhang.laboratory.ui.net.NameFakeService
import me.zhang.laboratory.ui.room.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Li on 6/16/2016 9:37 PM.
 */
class App : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        sContext = this
    }

    override fun onCreate() {
        super.onCreate()
        roomDb = databaseBuilder(applicationContext, AppDatabase::class.java, "room_db").build()
        nameFakeService = Retrofit.Builder().baseUrl("https://api.namefake.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NameFakeService::class.java)
    }

    companion object {
        private lateinit var sContext: App

        init {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }

        private lateinit var roomDb: AppDatabase
        private lateinit var nameFakeService: NameFakeService
        val context: Context
            get() = sContext

        fun getRoomDb(): AppDatabase {
            return roomDb
        }

        fun getNameFakeService(): NameFakeService {
            return nameFakeService
        }
    }
}