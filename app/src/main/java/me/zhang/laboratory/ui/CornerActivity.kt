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
import kotlinx.android.synthetic.main.activity_corner.*
import me.zhang.laboratory.R
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

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val itemData = parent?.getItemAtPosition(position)
        if (itemData is String) {
            when (itemData) {
                getString(R.string.string_layout_match) -> {
                    val set = ConstraintSet()
                    set.clone(constraintLayout)
                    set.connect(R.id.cornerLayout, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    set.connect(R.id.cornerLayout, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    set.connect(R.id.cornerLayout, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
                    set.connect(R.id.cornerLayout, ConstraintSet.BOTTOM, R.id.guideline5, ConstraintSet.TOP)
                    set.constrainWidth(R.id.cornerLayout, ConstraintSet.MATCH_CONSTRAINT)
                    set.constrainHeight(R.id.cornerLayout, ConstraintSet.MATCH_CONSTRAINT)
                    set.applyTo(constraintLayout)

                    seekBar.isEnabled = true
                }
                getString(R.string.string_layout_wrap) -> {
                    val set = ConstraintSet()
                    set.clone(constraintLayout)
                    set.connect(R.id.cornerLayout, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    set.connect(R.id.cornerLayout, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    set.clear(R.id.cornerLayout, ConstraintSet.RIGHT)
                    set.clear(R.id.cornerLayout, ConstraintSet.BOTTOM)
                    set.constrainWidth(R.id.cornerLayout, ConstraintSet.WRAP_CONTENT)
                    set.constrainHeight(R.id.cornerLayout, ConstraintSet.WRAP_CONTENT)
                    set.applyTo(constraintLayout)

                    seekBar.isEnabled = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corner)

        initViews()
        initSpinner()
        initNumberPicker()
        initSeekBar()
    }

    private fun initViews() {
        cornerLayout.removeAllViews()
        for (i in 0 until colors.size) {
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
            view.setBackgroundColor(ContextCompat.getColor(this, colors[i]))
            views.add(view)
        }
        views.forEach { cornerLayout.addView(it) }
    }

    private fun initSpinner() {
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this,
                R.array.cornerlayout_config_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun initNumberPicker() {
        numberPicker.minValue = 0
        numberPicker.maxValue = views.size
        numberPicker.setOnValueChangedListener { _, _, newVal ->
            cornerLayout.removeAllViews()
            val partialViews = views.subList(0, newVal)
            partialViews.forEach {
                cornerLayout.addView(it)
            }
            cornerLayout.requestLayout()
        }
    }

    private fun initSeekBar() {
        seekBar.max = 64
        seekBar.progress = 16
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cornerLayout.setPadding(progress.dp(this@CornerActivity).toInt())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

}
