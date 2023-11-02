package me.zhang.laboratory.ui.firebase

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.UUID


class TestFirebaseActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val crashButton = Button(this)
        @SuppressLint("SetTextI18n")
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw IllegalAccessError("Test Crash") // Force a crash
        }

        addContentView(
            crashButton, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    override fun onResume() {
        super.onResume()

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, UUID.randomUUID().toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "onResume()")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}