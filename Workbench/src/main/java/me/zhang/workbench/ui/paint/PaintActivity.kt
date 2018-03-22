package me.zhang.workbench.ui.paint

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.activity_paint.*
import me.zhang.workbench.R


/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lineButton -> {
                    paintView.useThisPainter(LinePainter(paintView))
                }
                R.id.rectangleButton -> {
                    paintView.useThisPainter(RectanglePainter(paintView))
                }
                R.id.ovalButton -> {
                    paintView.useThisPainter(OvalPainter(paintView))
                }
            }
        }
        colorButton.setOnClickListener { _ ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(Color.RED)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setOnColorSelectedListener { selectedColor ->
                        Toast.makeText(this, "0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show()
                    }
                    .setPositiveButton("Ok") { _, selectedColor, _ ->
                        changePaintColor(selectedColor)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .build()
                    .show()
        }
        clearButton.setOnClickListener { _ ->
            clearCanvas()
        }
    }

    private fun clearCanvas() {
        paintView.clearCanvas()
    }

    private fun changePaintColor(selectedColor: Int) {
        paintView.changePaintColor(selectedColor)
    }

}