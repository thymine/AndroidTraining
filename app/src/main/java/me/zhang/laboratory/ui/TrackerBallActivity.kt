package me.zhang.laboratory.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.databinding.ActivityTrackerBallBinding

class TrackerBallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTrackerBallBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}