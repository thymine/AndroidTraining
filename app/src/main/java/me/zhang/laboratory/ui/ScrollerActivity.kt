package me.zhang.laboratory.ui

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_scroller.*
import me.zhang.laboratory.R

@Suppress("UNUSED_PARAMETER")
class ScrollerActivity : AppCompatActivity() {

    private var scrollRange = 0 // should be larger than or equal to 0
    private var targetView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroller)

        targetView = textView // default
        scrollRange = seekBar.progress

        initSeekBar()
        initRadioGroup()
    }

    private fun initSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                scrollRange = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initRadioGroup() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.g -> targetView = linearLayout
                R.id.v -> targetView = textView
            }
        }
    }

    //region Scroll Content
    fun scrollLeft(view: View) {
        targetView?.scrollBy(-scrollRange, 0)
    }

    fun scrollUp(view: View) {
        targetView?.scrollBy(0, -scrollRange)
    }

    fun scrollRigth(view: View) {
        val scrollX = targetView?.scrollX ?: 0
        val scrollY = targetView?.scrollY ?: 0
        targetView?.scrollTo(scrollX + scrollRange, scrollY)
    }

    fun scrollDown(view: View) {
        val scrollX = targetView?.scrollX ?: 0
        val scrollY = targetView?.scrollY ?: 0
        targetView?.scrollTo(scrollX, scrollY + scrollRange)
    }
    //endregion

}
