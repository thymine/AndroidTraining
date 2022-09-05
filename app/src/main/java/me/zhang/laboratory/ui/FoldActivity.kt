package me.zhang.laboratory.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.databinding.ActivityFoldBinding

class FoldActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoldBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}