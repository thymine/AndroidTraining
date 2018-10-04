package me.zhang.workbench.ui

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.view.setPadding
import kotlinx.android.synthetic.main.activity_corner.*
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp

class CornerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val views = arrayListOf<View>()
    private val colors = arrayListOf(
            R.color.red,
            R.color.green,
            R.color.blue,
            R.color.yellow
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
        for (i in 0 until colors.size) {
            val view = View(this)
            val randWidthDp = (96 + Math.random() * 32).toInt() // [96, 128) dp
            val randHeightDp = (96 + Math.random() * 32).toInt() // [96, 128) dp
            view.layoutParams = ViewGroup.LayoutParams(randWidthDp.dp(this).toInt(), randHeightDp.dp(this).toInt())
            view.setBackgroundColor(ContextCompat.getColor(this, colors[i]))
            views.add(view)
        }
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
