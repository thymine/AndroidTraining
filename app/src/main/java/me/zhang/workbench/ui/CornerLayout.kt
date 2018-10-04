package me.zhang.workbench.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import me.zhang.workbench.utils.dp

/**
 *
 */
class CornerLayout : ViewGroup {

    private val border = Rect()
    private val borderPaint = Paint()
    private val borderWidth: Float

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setWillNotDraw(false)
        borderWidth = context?.let { 3.dp(context) } ?: 9f // px
        borderPaint.color = Color.RED
        borderPaint.strokeWidth = borderWidth
        borderPaint.style = Paint.Style.STROKE
        borderPaint.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = getMeasuredWidth(widthMeasureSpec)
        val measuredHeight = getMeasuredHeight(heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun getMeasuredWidth(widthMeasureSpec: Int): Int {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        var width = 0
        when (mode) {
            MeasureSpec.EXACTLY -> { // match_parent或具体值
                width = size
            }
            MeasureSpec.AT_MOST -> { // wrap_content
                var ltw = 0
                var rtw = 0
                var lbw = 0
                var rbw = 0
                for (i in 0 until childCount) {
                    when (i) {
                        0 -> ltw = getChildAt(i).measuredWidth
                        1 -> rtw = getChildAt(i).measuredWidth
                        2 -> lbw = getChildAt(i).measuredWidth
                        3 -> rbw = getChildAt(i).measuredWidth
                    }
                    width = Math.max(ltw, lbw) + Math.max(rtw, rbw) + (paddingLeft + paddingRight)
                }
            }
            MeasureSpec.UNSPECIFIED -> {

            }
        }
        return width
    }

    private fun getMeasuredHeight(heightMeasureSpec: Int): Int {
        val size = MeasureSpec.getSize(heightMeasureSpec)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        var height = 0
        when (mode) {
            MeasureSpec.EXACTLY -> { // match_parent或具体值
                height = size
            }
            MeasureSpec.AT_MOST -> { // wrap_content
                var lth = 0
                var rth = 0
                var lbh = 0
                var rbh = 0
                for (i in 0 until childCount) {
                    when (i) {
                        0 -> lth = getChildAt(i).measuredHeight
                        1 -> rth = getChildAt(i).measuredHeight
                        2 -> lbh = getChildAt(i).measuredHeight
                        3 -> rbh = getChildAt(i).measuredHeight
                    }
                    height = Math.max(lth, lbh) + Math.max(rth, rbh) + (paddingTop + paddingBottom)
                }
            }
            MeasureSpec.UNSPECIFIED -> {

            }
        }
        return height
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            when (i) {
                0 -> {
                    // 左上角
                    child.layout(
                            paddingLeft,
                            paddingTop,
                            child.measuredWidth + paddingLeft,
                            child.measuredHeight + paddingTop
                    )
                }
                1 -> {
                    // 右上角
                    child.layout(
                            measuredWidth - child.measuredWidth - paddingRight,
                            paddingTop,
                            measuredWidth - paddingRight,
                            child.measuredHeight + paddingTop
                    )
                }
                2 -> {
                    // 左下角
                    child.layout(
                            paddingLeft,
                            measuredHeight - child.measuredHeight - paddingBottom,
                            child.measuredWidth + paddingLeft,
                            measuredHeight - paddingBottom
                    )
                }
                3 -> {
                    // 右下角
                    child.layout(
                            measuredWidth - child.measuredWidth - paddingRight,
                            measuredHeight - child.measuredHeight - paddingBottom,
                            measuredWidth - paddingLeft,
                            measuredHeight - paddingBottom
                    )
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        border.set(0, 0, measuredWidth, measuredHeight)
        canvas?.drawRect(border, borderPaint)
    }

}