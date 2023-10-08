package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import me.zhang.laboratory.R
import me.zhang.laboratory.ui.view.CornerLayout.PositionalLayoutParams.Companion.LEFT_BOTTOM
import me.zhang.laboratory.ui.view.CornerLayout.PositionalLayoutParams.Companion.LEFT_TOP
import me.zhang.laboratory.ui.view.CornerLayout.PositionalLayoutParams.Companion.NONE
import me.zhang.laboratory.ui.view.CornerLayout.PositionalLayoutParams.Companion.RIGHT_BOTTOM
import me.zhang.laboratory.ui.view.CornerLayout.PositionalLayoutParams.Companion.RIGHT_TOP
import me.zhang.laboratory.utils.dp

/**
 *
 */
class CornerLayout : ViewGroup {

    private val border = Rect()
    private val borderPaint = Paint()
    private val borderWidth: Float

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
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

                var lthm = 0
                var rthm = 0
                var lbhm = 0
                var rbhm = 0
                for (i in 0 until childCount) {
                    val lp: MarginLayoutParams = getChildAt(i).layoutParams as MarginLayoutParams
                    when (i) {
                        0 -> {
                            ltw = getChildAt(i).measuredWidth
                            lthm = lp.leftMargin + lp.rightMargin
                        }

                        1 -> {
                            rtw = getChildAt(i).measuredWidth
                            rthm = lp.leftMargin + lp.rightMargin
                        }

                        2 -> {
                            lbw = getChildAt(i).measuredWidth
                            lbhm = lp.leftMargin + lp.rightMargin
                        }

                        3 -> {
                            rbw = getChildAt(i).measuredWidth
                            rbhm = lp.leftMargin + lp.rightMargin
                        }
                    }
                    width =
                        ltw.coerceAtLeast(lbw) + rtw.coerceAtLeast(rbw) + (paddingLeft + paddingRight)
                    +lthm.coerceAtLeast(lbhm) + rthm.coerceAtLeast(rbhm)
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

                var ltvm = 0
                var rtvm = 0
                var lbvm = 0
                var rbvm = 0
                for (i in 0 until childCount) {
                    val lp = getChildAt(i).layoutParams as MarginLayoutParams
                    when (i) {
                        0 -> {
                            lth = getChildAt(i).measuredHeight
                            ltvm = lp.leftMargin + lp.rightMargin
                        }

                        1 -> {
                            rth = getChildAt(i).measuredHeight
                            rtvm = lp.leftMargin + lp.rightMargin
                        }

                        2 -> {
                            lbh = getChildAt(i).measuredHeight
                            lbvm = lp.leftMargin + lp.rightMargin
                        }

                        3 -> {
                            rbh = getChildAt(i).measuredHeight
                            rbvm = lp.leftMargin + lp.rightMargin
                        }
                    }
                    height =
                        lth.coerceAtLeast(lbh) + rth.coerceAtLeast(rbh) + (paddingTop + paddingBottom)
                    +ltvm.coerceAtLeast(lbvm) + rtvm.coerceAtLeast(rbvm)
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
            val lp = child.layoutParams as PositionalLayoutParams
            val leftMargin = lp.leftMargin
            val rightMargin = lp.rightMargin
            val topMargin = lp.topMargin
            val bottomMargin = lp.bottomMargin
            val position = lp.position

            if ((i == 0 && position == NONE) || position == LEFT_TOP) {
                // 左上角
                child.layout(
                    paddingLeft + leftMargin,
                    paddingTop + topMargin,
                    child.measuredWidth + paddingLeft + leftMargin,
                    child.measuredHeight + paddingTop + topMargin
                )
            } else if ((i == 1 && position == NONE) || position == RIGHT_TOP) {
                // 右上角
                child.layout(
                    measuredWidth - child.measuredWidth - paddingRight - rightMargin,
                    paddingTop + topMargin,
                    measuredWidth - paddingRight - rightMargin,
                    child.measuredHeight + paddingTop + topMargin
                )
            } else if ((i == 2 && position == NONE) || position == LEFT_BOTTOM) {
                // 左下角
                child.layout(
                    paddingLeft + leftMargin,
                    measuredHeight - child.measuredHeight - paddingBottom - bottomMargin,
                    child.measuredWidth + paddingLeft + leftMargin,
                    measuredHeight - paddingBottom - bottomMargin
                )
            } else if ((i == 3 && position == NONE) || position == RIGHT_BOTTOM) {
                // 右下角
                child.layout(
                    measuredWidth - child.measuredWidth - paddingRight - rightMargin,
                    measuredHeight - child.measuredHeight - paddingBottom - bottomMargin,
                    measuredWidth - paddingLeft - rightMargin,
                    measuredHeight - paddingBottom - bottomMargin
                )
            }
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return PositionalLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return PositionalLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return PositionalLayoutParams(p)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        border.set(0, 0, measuredWidth, measuredHeight)
        canvas.drawRect(border, borderPaint)
    }

    class PositionalLayoutParams : MarginLayoutParams {

        companion object {
            const val LEFT_TOP = 0
            const val RIGHT_TOP = 1
            const val LEFT_BOTTOM = 2
            const val RIGHT_BOTTOM = 3
            const val NONE = -1
        }

        var position: Int? = NONE

        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: LayoutParams?) : super(source)
        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {
            // 获取layout_position属性
            val ta = c?.obtainStyledAttributes(attrs, R.styleable.CornerLayout_Layout)
            position = ta?.getInt(R.styleable.CornerLayout_Layout_layout_position, NONE)
            ta?.recycle()
        }

    }

}