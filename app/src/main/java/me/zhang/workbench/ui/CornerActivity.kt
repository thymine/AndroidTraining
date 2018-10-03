package me.zhang.workbench.ui

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_corner.*
import me.zhang.workbench.R

class CornerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

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
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corner)

        initSpinner()
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

}
