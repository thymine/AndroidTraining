package me.zhang.laboratory.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_record_progress_bar.*
import me.zhang.laboratory.R
import me.zhang.laboratory.ui.RecordProgressBarActivity.Const.MAX_PROGRESS
import me.zhang.laboratory.ui.bean.PartBean
import kotlin.random.Random

class RecordProgressBarActivity : AppCompatActivity() {

    object Const {
        const val MAX_PROGRESS = 120 * 1000L /* ms */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_progress_bar)
        recordProgressBar.setDrawingParts(emptyList())
        recordProgressBar.setMaxProgress(MAX_PROGRESS)
    }

    fun addProgressRandomly(@Suppress("UNUSED_PARAMETER") view: View) {
        val partBean = PartBean()
        partBean.duration = Random.nextLong(0, MAX_PROGRESS.div(6)) // [0, 20 * 1000]
        recordProgressBar.addDrawingPart(partBean)
    }

}