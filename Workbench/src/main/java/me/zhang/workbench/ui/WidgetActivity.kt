package me.zhang.workbench.ui

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_widget.*
import me.zhang.workbench.R
import me.zhang.workbench.utils.toPixel

class WidgetActivity : Activity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val LOG_TAG: String = "WidgetActivity"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position)
        Log.d(LOG_TAG, selectedItem as String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget)

        spinner()
        popupWindow()
    }

    private fun spinner() {
        planetsSpinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        val planetsAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        planetsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        planetsSpinner.adapter = planetsAdapter
    }

    private fun popupWindow() {
        anchorViewCenter.setOnClickListener({
            getPopupWindow().showAsDropDown(anchorViewCenter)
        })

        anchorViewTopEnd.setOnClickListener({
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getPopupWindow().showAsDropDown(anchorViewTopEnd, 0, 0, Gravity.END)
            } else {
                getPopupWindow().showAsDropDown(anchorViewTopEnd)
            }
        })
    }

    private fun getPopupWindow(): PopupWindow {
        val contentView = View(this)
        contentView.setBackgroundColor(Color.BLUE)

        val popupWindow = PopupWindow(contentView, 128.toPixel(this).toInt(), 64.toPixel(this).toInt(), true)
        popupWindow.setBackgroundDrawable(ColorDrawable())

        return popupWindow
    }

}
