package me.zhang.laboratory.ui

import android.os.Bundle
import android.view.View
import me.zhang.laboratory.databinding.ActivityRecordProgressBarBinding
import me.zhang.laboratory.ui.RecordProgressBarActivity.Const.MAX_PROGRESS
import me.zhang.laboratory.ui.bean.PartBean
import kotlin.random.Random

class RecordProgressBarActivity : BaseActivity() {

    object Const {
        const val MAX_PROGRESS = 120 * 1000L /* ms */
    }

    private lateinit var binding: ActivityRecordProgressBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordProgressBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recordProgressBar.setDrawingParts(emptyList())
        binding.recordProgressBar.setMaxProgress(MAX_PROGRESS)
    }

    fun addProgressRandomly(@Suppress("UNUSED_PARAMETER") view: View) {
        val partBean = PartBean()
        partBean.duration = Random.nextLong(0, MAX_PROGRESS.div(6)) // [0, 20 * 1000]
        binding.recordProgressBar.addDrawingPart(partBean)
    }

}