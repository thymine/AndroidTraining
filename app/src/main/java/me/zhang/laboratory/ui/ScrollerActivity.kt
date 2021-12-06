package me.zhang.laboratory.ui

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.R
import me.zhang.laboratory.databinding.ActivityScrollerBinding

@Suppress("UNUSED_PARAMETER")
class ScrollerActivity : AppCompatActivity() {

    private var scrollRange = 0 // should be larger than or equal to 0
    private var targetView: View? = null

    private lateinit var binding: ActivityScrollerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        targetView = binding.textView // default
        scrollRange = binding.ySeekBar.progress

        initSeekBar()
        initRadioGroup()
    }

    private fun initSeekBar() {
        binding.ySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.g -> targetView = binding.linearLayout
                R.id.v -> targetView = binding.textView
            }
        }
    }

    //region Scroll
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

    //region Smooth Scroll
    fun smoothScrollUp(v: View) {
        binding.linearLayout.smoothScrollBy(0, -256)
    }

    fun smoothScrollDown(v: View) {
        binding.linearLayout.smoothScrollBy(0, 256)
    }

    fun smoothScrollLeft(v: View) {
        binding.linearLayout.smoothScrollBy(-256, 0)
    }

    fun smoothScrollRight(v: View) {
        binding.linearLayout.smoothScrollBy(256, 0)
    }
    //endregion

}
