package me.zhang.workbench.ui.paint

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.view.MotionEvent

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
abstract class BaseShapePainter(paintView: PaintView) : ShapePainter(paintView) {

    private val path = Path()
    private var startX = 0f
    private var startY = 0f

    override fun onDraw(canvas: Canvas, bitmapBuffer: Bitmap) {
        canvas.drawPath(path, getPaint())
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent, bitmapCanvas: Canvas): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
            }
            MotionEvent.ACTION_MOVE -> {
                path.reset()
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
                addShape(path, left, top, right, bottom)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                bitmapCanvas.drawPath(path, getPaint())
                path.reset()
                invalidate()
            }
        }
        return true
    }

    abstract fun addShape(path: Path, left: Float, top: Float, right: Float, bottom: Float)

}