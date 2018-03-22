package me.zhang.workbench.ui.paint

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
open class RectanglePainter(paintView: PaintView) : ShapePainter(paintView) {

    private val rectF = RectF()
    private var startX = 0f
    private var startY = 0f

    override fun onDraw(canvas: Canvas, bitmapBuffer: Bitmap) {
        drawShape(canvas, rectF, getPaint())
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent, bitmapCanvas: Canvas, paint: Paint): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rectF.setEmpty()
                startX = x
                startY = y
            }
            MotionEvent.ACTION_MOVE -> {
                rectF.setEmpty()
                val left: Float
                val top: Float
                val right: Float
                val bottom: Float
                if (startX < x) {
                    left = startX
                    right = x
                } else {
                    left = x
                    right = startX
                }
                if (startY < y) {
                    top = startY
                    bottom = y
                } else {
                    top = y
                    bottom = startY
                }
                rectF.set(left, top, right, bottom)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                drawShape(bitmapCanvas, rectF, getPaint())
                invalidate()
            }
        }
        return true
    }

    open fun drawShape(canvas: Canvas, rectF: RectF, paint: Paint) {
        canvas.drawRect(rectF, paint)
    }

}