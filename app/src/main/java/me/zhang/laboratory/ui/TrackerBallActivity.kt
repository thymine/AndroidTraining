package me.zhang.laboratory.ui

import android.os.Bundle
import me.zhang.laboratory.databinding.ActivityTrackerBallBinding

class TrackerBallActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTrackerBallBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}