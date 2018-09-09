package me.zhang.workbench.ui.paint

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
abstract class ShapePainter(private val paintPresenter: PaintContract.PaintPresenter) {

    abstract fun onTouchEvent(event: MotionEvent, bitmapCanvas: Canvas): Boolean

    internal fun invalidate() {
        paintPresenter.redraw()
    }

    internal fun getPaint(): Paint {
        return paintPresenter.getPaint()
    }

    internal fun getPaintPresenter(): PaintContract.PaintPresenter {
        return paintPresenter
    }

    fun newShape(path: Path) = Shape(path, getPaint().color)

}