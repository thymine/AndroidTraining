package me.zhang.laboratory.ui

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.databinding.ActivityFoldBinding
import me.zhang.laboratory.ui.view.FoldView3

class FoldActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sbFactor.progress = (FoldView3.FACTOR * 100).toInt()
        binding.sbFactor.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.fv.factor = progress * 1f / 100.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

}