package me.zhang.workbench.text

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_draw_text.*
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp

class DrawTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_text)

        initAutoSizingTextView()
    }

    private fun initAutoSizingTextView() {
        autoSizingTextView.setText("Pie")
        autoSizingTextView.setTextColor(Color.CYAN)
        autoSizingTextView.setTextSize(32.dp())
    }

}
