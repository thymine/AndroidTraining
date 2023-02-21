package me.zhang.laboratory.ui

import android.os.Bundle
import android.widget.SeekBar
import me.zhang.laboratory.databinding.ActivityAndroidGraphicsCameraBinding

class AndroidGraphicsCameraActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAndroidGraphicsCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val xSeekBar = binding.xSeekBar
        val zSeekBar = binding.zSeekBar
        val ySeekBar = binding.ySeekBar
        val cameraView = binding.cameraView

        ySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cameraView.setRotateY(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        xSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cameraView.setRotateX(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        zSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cameraView.setRotateZ(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

}