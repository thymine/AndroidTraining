package me.zhang.workbench.ui.paint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.workbench.utils.dp

/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintView : View {

    private var shapePainter: ShapePainter = LinePainter(this)
    internal val paint = Paint()
    private var bitmapBuffer: Bitmap? = null
    private var bitmapCanvas: Canvas? = null

    private var shouldClearCanvas: Boolean? = null

    fun useThisPainter(painter: ShapePainter) {
        shapePainter = painter
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 3.dp()
        paint.color = Color.RED
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

        if (bitmapBuffer != null) {
            shapePainter.onDraw(canvas, bitmapBuffer!!)
        }

        // 根据shouldClearCanvas字段判断是否应该清空画布内容
        shouldClearCanvas?.let {
            if (it) {
                bitmapBuffer?.eraseColor(Color.TRANSPARENT)
                canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
                shouldClearCanvas = false
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return bitmapCanvas?.let { shapePainter.onTouchEvent(event, bitmapCanvas!!, paint) }
                ?: false
    }

    fun changePaintColor(selectedColor: Int) {
        paint.color = selectedColor
    }

    fun clearCanvas() {
        bitmapCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        shouldClearCanvas = true
        invalidate()
    }

}