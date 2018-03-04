package me.zhang.workbench.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_widget.*
import me.zhang.workbench.R

class WidgetActivity : Activity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position)
        Toast.makeText(this, selectedItem as String, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget)

        val planetsSpinner = s_planets
        planetsSpinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        val planetsAdapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        planetsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        planetsSpinner.adapter = planetsAdapter
    }

}
