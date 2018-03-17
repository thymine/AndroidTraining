package me.zhang.workbench.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.utils.dp

/**
 * Created by Zhang on 3/17/2018 8:23 PM.
 */
class RulerView : View {

    companion object {
        const val RULER_LENGTH = 5 * 10 // 50mm
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rulerRectF = RectF()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.dp(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(size / 2, mode))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rulerWidth = 4f * measuredWidth / 5
        val rulerHeight = 3f * measuredHeight / 4

        val rulerLeft = measuredWidth / 2 - rulerWidth / 2
        val rulerTop = measuredHeight / 2 - rulerHeight / 2
        val rulerRight = rulerLeft + rulerWidth
        val rulerBottom = rulerTop + rulerHeight

        rulerRectF.set(rulerLeft, rulerTop, rulerRight, rulerBottom)
        canvas.drawRoundRect(rulerRectF, 6.dp(context), 6.dp(context), paint)

        canvas.save()
        // 总刻度的长度（px）
        val scaleLength = 9f * rulerWidth / 10
        // 一个刻度的长度（px）
        val oneScaleLength = scaleLength / RULER_LENGTH
        // 第一个刻度的坐标（初始坐标）
        val firstScaleX = (rulerWidth - scaleLength) / 2
        // 最长刻度的长度
        val longLength = 3f * rulerHeight / 5
        // 中等刻度的长度
        val mediumLength = 2f * rulerHeight / 5
        // 最短刻度的长度
        val shortLength = 1f * rulerHeight / 4
        // 移动画布x=0, y=0到尺的左下角刻度为0处
        canvas.translate(rulerLeft + firstScaleX, rulerTop + rulerHeight)
        // 开始绘制各个刻度
        for (i in 0..RULER_LENGTH) {
            when {
                i % 10 == 0 -> {
                    canvas.drawLine(0f, 0f, 0f, -longLength, paint)
                    val index = i / 10
                    var text = index.toString()
                    val offset = paint.measureText(text) // 文本宽度，以数字为标准设置绘制偏移量
                    if (i == 0) {
                        text = "$index cm" // 第一个刻度带有cm提示
                    }
                    paint.textSize = 14.dp(context)
                    // 在刻度正上方绘制文本
                    canvas.drawText(text, -offset / 2, -longLength - offset, paint)
                }
                i % 5 == 0 -> canvas.drawLine(0f, 0f, 0f, -mediumLength, paint)
                else -> canvas.drawLine(0f, 0f, 0f, -shortLength, paint)
            }
            canvas.translate(oneScaleLength, 0f)
        }
        canvas.restore()
    }

}