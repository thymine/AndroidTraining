package me.zhang.workbench.ui.paint

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
class LinePainter(paintView: PaintView) : ShapePainter(paintView) {

    private var preX = 0f
    private var preY = 0f

    override fun onDraw(canvas: Canvas, bitmapBuffer: Bitmap) {
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent, bitmapCanvas: Canvas, paint: Paint): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                preX = x
                preY = y
            }
            MotionEvent.ACTION_MOVE -> {
                bitmapCanvas.drawLine(preX, preY, x, y, paint)
                invalidate()

                preX = x
                preY = y
            }
        }
        return true
    }

}