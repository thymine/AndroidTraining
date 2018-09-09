package me.zhang.workbench.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.workbench.utils.dp

/**
 * Created by Zhang on 3/18/2018 11:48 AM.
 */
class LineDrawingView1 : View {

    private val paint = Paint()
    private val path = android.graphics.Path()
    private var preX = 0f
    private var preY = 0f

    private var bitmapBuffer: Bitmap? = null
    private var bitmapCanvas: Canvas? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4.dp()

        setBackgroundColor(Color.YELLOW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (bitmapBuffer == null) {
            bitmapBuffer = createBitmap(measuredWidth, measuredHeight, ARGB_8888)
            bitmapCanvas = Canvas(bitmapBuffer)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
        canvas.drawPath(path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
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
                // 抬起手指，将绘制路径记录到BitmapBuffer中，同时绘制到View上
                bitmapCanvas?.drawPath(path, paint)
                invalidate()
            }
        }
        return true
    }

}