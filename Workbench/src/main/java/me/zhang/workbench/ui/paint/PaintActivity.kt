package me.zhang.workbench.ui.paint

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_paint.*
import me.zhang.workbench.R

/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)
        radioGroup.setOnCheckedChangeListener({ _, checkedId ->
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
        })
    }

}