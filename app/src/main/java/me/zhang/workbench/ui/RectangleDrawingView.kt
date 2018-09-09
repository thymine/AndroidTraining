package me.zhang.workbench.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.createBitmap
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.workbench.utils.dp

/**
 * Created by zhangxiangdong on 2018/3/19.
 */
class RectangleDrawingView : View {

    private val paint = Paint()
    private var bitmapBuffer: Bitmap? = null
    private var bitmapCanvas: Canvas? = null
    private var startX = 0f
    private var startY = 0f
    private var rectF = RectF()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.STROKE
        paint.color = Color.YELLOW
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 3.dp()

        setBackgroundColor(Color.RED)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (bitmapBuffer == null) {
            bitmapBuffer = createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
            bitmapCanvas = Canvas(bitmapBuffer)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
        canvas.drawRect(rectF, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
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

                // 确定rectangle的左上角和右下角
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
                bitmapCanvas?.drawRect(rectF, paint)
                invalidate()
            }
        }
        return true
    }

}