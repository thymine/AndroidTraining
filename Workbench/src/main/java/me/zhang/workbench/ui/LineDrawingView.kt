package me.zhang.workbench.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.workbench.utils.dp

/**
 * Created by Zhang on 3/18/2018 11:20 AM.
 */
class LineDrawingView : View {

    private var preX = 0f
    private var preY = 0f
    private var currentX = 0f
    private var currentY = 0f

    // 保存所有的绘图历史
    private var bitmapBuffer: Bitmap? = null
    private var bitmapCanvas: Canvas? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        paint.strokeWidth = 5.dp()

        setBackgroundColor(Color.GRAY)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (bitmapBuffer == null) {
            val width = measuredWidth
            val height = measuredHeight
            // 创建缓冲Bitmap
            bitmapBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            // 创建对应的画布
            bitmapCanvas = Canvas(bitmapBuffer)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 将Bitmap中的内容绘制到View上
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 手指按下，记录第一个点的坐标
                preX = x
                preY = y
            }
            MotionEvent.ACTION_MOVE -> {
                // 手指移动，记录当前点的坐标
                currentX = x
                currentY = y
                // 绘制移动产生的线条
                bitmapCanvas?.drawLine(preX, preY, currentX, currentY, paint)
                invalidate()

                // 重新记录之前的点坐标
                preX = currentX
                preY = currentY
            }
            MotionEvent.ACTION_UP -> {
                invalidate()
            }
        }
        return true
    }

}