package me.zhang.laboratory.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.databinding.ActivityVerticalLayoutBinding

class VerticalLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVerticalLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}