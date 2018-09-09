package me.zhang.workbench.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.workbench.R

/**
 * Created by zhangxiangdong on 2018/4/26 17:19.
 */
class ScratchTicketView : View {

    companion object {
        const val FINGER = 50f
    }

    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var coverBitmap: Bitmap
    private lateinit var coverCanvas: Canvas
    private var curX = 0f
    private var curY = 0f

    init {
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        clearPaint.strokeJoin = Paint.Join.ROUND
        clearPaint.strokeCap = Paint.Cap.ROUND
        clearPaint.strokeWidth = FINGER
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setBackgroundResource(R.drawable.androidpartyx)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        coverBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        coverCanvas = Canvas(coverBitmap)
        coverCanvas.drawColor(Color.LTGRAY)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(coverBitmap, 0f, 0f, null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                curX = x
                curY = y
            }
            MotionEvent.ACTION_MOVE -> {
                coverCanvas.drawLine(curX, curY, x, y, clearPaint)
                curX = x
                curY = y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                invalidate()
            }
        }
        return true
    }

}