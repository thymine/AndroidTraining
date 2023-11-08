package me.zhang.laboratory.ui.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import java.util.UUID


class TestFirebaseActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "TestFirebaseActivity"
    }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContent { Main() }
    }

    override fun onResume() {
        super.onResume()

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, UUID.randomUUID().toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "onResume()")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    @Composable
    fun Main() {
        Box {
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    throw IllegalAccessError("Test Crash") // Force a crash
                }) {
                    Text("Test Crash")
                }
                Button(onClick = {
                    Firebase.messaging.subscribeToTopic("weather")
                        .addOnCompleteListener { task ->
                            val msg = if (!task.isSuccessful) {
                                "Subscribe failed"
                            } else {
                                "Subscribed"
                            }
                            Log.d(TAG, msg)
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                }) {
                    Text("Subscribe weather topic")
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }

}