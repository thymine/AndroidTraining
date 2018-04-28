package me.zhang.workbench.text.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp

/**
 * Created by Zhang on 2018/4/21 22:45.
 */
class TextView : View {

    private val text: String
    private val textPaint = TextPaint()
    private val textRect = Rect()

    init {
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.style = Paint.Style.FILL
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextView)

        text = a.getString(R.styleable.TextView_text)

        val textColor = a.getColor(R.styleable.TextView_textColor, Color.BLACK)
        textPaint.color = textColor
        val strokeWidth = a.getDimension(R.styleable.TextView_strokeWidth, 1.dp())
        textPaint.strokeWidth = strokeWidth

        val textSize = a.getDimension(R.styleable.TextView_textSize, 14.dp())
        textPaint.textSize = textSize

        textPaint.getTextBounds(text, 0, text.length, textRect)

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec, textRect.width() + paddingLeft + paddingRight)
        val height = measureHeight(heightMeasureSpec, textRect.height() + paddingTop + paddingBottom)
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(widthMeasureSpec: Int, textWidth: Int): Int {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        var width = 0
        when (mode) {
            MeasureSpec.EXACTLY -> {
                // 宽度为match_parent或者具体数值
                width = size
            }
            MeasureSpec.AT_MOST -> {
                width = textWidth
            }
            MeasureSpec.UNSPECIFIED -> {

            }
        }
        return width
    }

    private fun measureHeight(heightMeasureSpec: Int, textHeight: Int): Int {
        val size = MeasureSpec.getSize(heightMeasureSpec)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        var height = 0
        when (mode) {
            MeasureSpec.EXACTLY -> {
                height = size
            }
            MeasureSpec.AT_MOST -> {
                height = textHeight
            }
            MeasureSpec.UNSPECIFIED -> {

            }
        }
        return height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 水平居中绘制文本
        val x = (measuredWidth - textRect.width()) / 2f

        // 垂直居中绘制文本
        // 获取baseline在y方向的坐标值
        val y = measuredHeight / 2 + (textPaint.descent() - textPaint.ascent()) / 2 - textPaint.descent()

        canvas.drawText(text, x, y, textPaint)
    }

}