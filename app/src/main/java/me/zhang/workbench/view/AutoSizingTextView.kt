package me.zhang.workbench.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.utils.dp

class AutoSizingTextView : View {

    private var text: String? = null
    private val paint = Paint()
    private val bounds: Rect = Rect()

    init {
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 3.dp() // default stroke width
        paint.color = Color.BLACK // default text color
        paint.textSize = 18.dp() // default text size
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setText(text: String?) {
        if (text == null) {
            this.text = ""
        }
        this.text = text
        requestLayout()
        invalidate()
    }

    fun setTextColor(@ColorInt color: Int) {
        paint.color = color
        invalidate()
    }

    fun setTextSize(textSize: Float) {
        paint.textSize = textSize
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        paint.getTextBounds(text, 0, text?.length ?: 0, bounds)
        val width = measureWidth(widthMeasureSpec, bounds.width())
        val height = measureHeight(heightMeasureSpec, bounds.height())
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(widthMeasureSpec: Int, textWidth: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var width = 0
        if (mode == MeasureSpec.EXACTLY) {
            width = size
        } else if (mode == MeasureSpec.AT_MOST) {
            width = textWidth + paddingLeft + paddingRight
        }
        if (width == 0) {
            width = 48.dp().toInt() // min width
        }
        return width
    }

    private fun measureHeight(heightMeasureSpec: Int, textHeight: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        var height = 0
        if (mode == MeasureSpec.EXACTLY) {
            height = size
        } else if (mode == MeasureSpec.AT_MOST) {
            height = textHeight + paddingTop + paddingBottom
        }
        if (height == 0) {
            height = 48.dp().toInt() // min height
        }
        return height
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /* draw text on center of view */
        text?.let {
            val x = ((width - bounds.right) / 2).toFloat()
            val y = height / 2 + ((paint.descent() - paint.ascent()) / 2 - paint.descent())
            canvas.drawText(text, x, y, paint)
        }
    }

}