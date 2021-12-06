package me.zhang.laboratory.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import me.zhang.laboratory.R
import me.zhang.laboratory.databinding.ActivityCornerBinding
import me.zhang.laboratory.ui.view.CornerLayout
import me.zhang.laboratory.utils.dp

class CornerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val views = arrayListOf<View>()
    private val colors = listOf(
        R.color.red,
        R.color.green,
        R.color.blue,
        R.color.yellow
    )
    private val positions = arrayListOf(
        CornerLayout.PositionalLayoutParams.LEFT_TOP,
        CornerLayout.PositionalLayoutParams.RIGHT_TOP,
        CornerLayout.PositionalLayoutParams.LEFT_BOTTOM,
        CornerLayout.PositionalLayoutParams.RIGHT_BOTTOM
    )
    private lateinit var binding: ActivityCornerBinding

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val itemData = parent?.getItemAtPosition(position)
        if (itemData is String) {
            when (itemData) {
                getString(R.string.string_layout_match) -> {
                    val set = ConstraintSet()
                    set.clone(binding.constraintLayout)
                    set.connect(R.id.cornerLayout, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    set.connect(R.id.cornerLayout, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    set.connect(R.id.cornerLayout, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
                    set.connect(R.id.cornerLayout, ConstraintSet.BOTTOM, R.id.guideline5, ConstraintSet.TOP)
                    set.constrainWidth(R.id.cornerLayout, ConstraintSet.MATCH_CONSTRAINT)
                    set.constrainHeight(R.id.cornerLayout, ConstraintSet.MATCH_CONSTRAINT)
                    set.applyTo(binding.constraintLayout)

                    binding.ySeekBar.isEnabled = true
                }
                getString(R.string.string_layout_wrap) -> {
                    val set = ConstraintSet()
                    set.clone(binding.constraintLayout)
                    set.connect(R.id.cornerLayout, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    set.connect(R.id.cornerLayout, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    set.clear(R.id.cornerLayout, ConstraintSet.RIGHT)
                    set.clear(R.id.cornerLayout, ConstraintSet.BOTTOM)
                    set.constrainWidth(R.id.cornerLayout, ConstraintSet.WRAP_CONTENT)
                    set.constrainHeight(R.id.cornerLayout, ConstraintSet.WRAP_CONTENT)
                    set.applyTo(binding.constraintLayout)

                    binding.ySeekBar.isEnabled = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCornerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initSpinner()
        initNumberPicker()
        initSeekBar()
    }

    private fun initViews() {
        binding.cornerLayout.removeAllViews()
        for (element in colors) {
            val view = View(this)
            val randWidthDp = (96 + Math.random() * 32).toInt() // [96, 128)
            val randHeightDp = (96 + Math.random() * 32).toInt() // [96, 128)
            val margin = (12 + Math.random() * 12).toInt() // [12, 24)

            val lp = CornerLayout.PositionalLayoutParams(
                randWidthDp.dp(this).toInt(),
                randHeightDp.dp(this).toInt()
            )
            lp.topMargin = margin.dp(this).toInt()
            lp.rightMargin = margin.dp(this).toInt()
            lp.rightMargin = margin.dp(this).toInt()
            lp.bottomMargin = margin.dp(this).toInt()
            val removedIndex = (positions.size * Math.random()).toInt()
            lp.position = positions.removeAt(removedIndex)

            view.layoutParams = lp
            view.setBackgroundColor(ContextCompat.getColor(this, element))
            views.add(view)
        }
        views.forEach { binding.cornerLayout.addView(it) }
    }

    private fun initSpinner() {
        binding.spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.cornerlayout_config_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }

    private fun initNumberPicker() {
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = views.size
        binding.numberPicker.setOnValueChangedListener { _, _, newVal ->
            binding.cornerLayout.removeAllViews()
            val partialViews = views.subList(0, newVal)
            partialViews.forEach {
                binding.cornerLayout.addView(it)
            }
            binding.cornerLayout.requestLayout()
        }
    }

    private fun initSeekBar() {
        binding.ySeekBar.max = 64
        binding.ySeekBar.progress = 16
        binding.ySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.cornerLayout.setPadding(progress.dp(this@CornerActivity).toInt())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

}
