package me.zhang.workbench.ui.paint

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
abstract class ShapePainter(private val paintView: PaintView) {

    abstract fun onDraw(canvas: Canvas, bitmapBuffer: Bitmap)

    abstract fun onTouchEvent(event: MotionEvent, bitmapCanvas: Canvas, paint: Paint): Boolean

    internal fun invalidate() {
        paintView.invalidate()
    }

    internal fun getPaint(): Paint {
        return paintView.paint
    }

}