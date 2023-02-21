package me.zhang.laboratory.ui

import android.os.Bundle
import me.zhang.laboratory.databinding.ActivityRotaryKnobBinding

class RotaryKnobActivity : BaseActivity() {

    private lateinit var binding: ActivityRotaryKnobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRotaryKnobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.to33.setOnClickListener {
            binding.rotaryKnob.setContent("1段", "教育经历")
            binding.rotaryKnob.toProgress(33)
        }
        binding.to67.setOnClickListener {
            binding.rotaryKnob.setContent("2段", "实习经历")
            binding.rotaryKnob.toProgress(67)
        }
        binding.to100.setOnClickListener {
            binding.rotaryKnob.setContent("3段", "工作经历")
            binding.rotaryKnob.toProgress(100)
        }
    }
}
