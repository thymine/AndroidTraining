package me.zhang.laboratory.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rotary_knob.*
import me.zhang.laboratory.R

class RotaryKnobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotary_knob)

        to33.setOnClickListener {
            rotaryKnob.setContent("1段", "教育经历")
            rotaryKnob.toProgress(33)
        }
        to67.setOnClickListener {
            rotaryKnob.setContent("2段", "实习经历")
            rotaryKnob.toProgress(67)
        }
        to100.setOnClickListener {
            rotaryKnob.setContent("3段", "工作经历")
            rotaryKnob.toProgress(100)
        }
    }
}
