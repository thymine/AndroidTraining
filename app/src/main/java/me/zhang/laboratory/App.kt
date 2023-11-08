package me.zhang.laboratory

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room.databaseBuilder
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
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
        Firebase.messaging.isAutoInitEnabled = true
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
        retrieveNewToken()
        createFcmNotificationChannel()

        roomDb = databaseBuilder(applicationContext, AppDatabase::class.java, "room_db").build()
        nameFakeService = Retrofit.Builder().baseUrl("https://api.namefake.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NameFakeService::class.java)
    }

    private fun createFcmNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val id = getString(R.string.fcm_channel_id)
            val name = getString(R.string.fcm_channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            channel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    private fun retrieveNewToken() {
        // Once the token is retrieved, it is advised that you store the token in your backend database of choice
        // as you will need to use the token to send messages via FCM to that specific device.
        // It is recommended you fetch and update the token each time the application boots in-case it has changed.
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

            // Gets a handle to the clipboard service.
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // Creates a new text clip to put on the clipboard.
            val clip: ClipData = ClipData.newPlainText("FCM registration token", token)
            // Set the clipboard's primary clip.
            clipboard.setPrimaryClip(clip)
        })
    }

    companion object {
        private const val TAG = "App"

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