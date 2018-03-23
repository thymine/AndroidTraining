package me.zhang.workbench.ui.paint

import android.graphics.Canvas
import android.graphics.Path
import android.view.MotionEvent

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
class LinePainter(paintPresenter: PaintContract.PaintPresenter) : ShapePainter(paintPresenter) {

    private val path = Path()
    private var preX = 0f
    private var preY = 0f

    override fun onTouchEvent(event: MotionEvent, bitmapCanvas: Canvas): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                getPaintPresenter().setVisualTempPath(path)
                preX = x
                preY = y
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                val controlX = (preX + x) / 2
                val controlY = (preY + y) / 2
                path.quadTo(controlX, controlY, x, y)
                invalidate()

                preX = x
                preY = y
            }
            MotionEvent.ACTION_UP -> {
                getPaintPresenter().addDrawingPath(Path(path))
                path.reset()
                invalidate()
            }
        }
        return true
    }

}