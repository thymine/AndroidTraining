package me.zhang.laboratory.ui

import android.os.Bundle
import me.zhang.laboratory.databinding.ActivityVerticalLayoutBinding

class VerticalLayoutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVerticalLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}