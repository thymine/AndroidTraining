package me.zhang.workbench.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.utils.dp
import java.util.*

/**
 * Created by Zhang on 3/16/2018 10:40 PM.
 */
class WatchView : View {

    companion object {
        const val WHAT_TICK: Int = 100
        const val ONE_SECOND_IN_MILLIS: Long = 1000 // 1000ms
        const val ONE_MINUTE_IN_MILLIS: Long = 60 * 1000 // 60,000ms
        const val ONE_HOUR_IN_MILLIS: Long = 60 * 60 * 1000 // 3,600,000ms
        const val TIME_PER_FRAME: Long = 16 // ms
        const val DEGREE_ONE_GRID: Float = 6f // °
        const val CA: Float = 360f // Circumferential Angle
    }

    private val calendar: Calendar = Calendar.getInstance()

    // 当前秒针所指的角度
    private var currentSecondHandDegree: Float = 0.0f
    // 当前分针所指的角度
    private var currentMinuteHandDegree: Float = 0.0f
    // 当前时针所指的角度
    private var currentHourHandDegree: Float = 0.0f

    // 秒针画笔
    private val handPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 表盘画笔
    private val watchFacePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val tickHandler: Handler = Handler({ msg: Message? ->
        when (msg?.what) {
            WHAT_TICK -> {
                redraw()
            }
        }
        return@Handler true
    })

    private fun redraw() {
        invalidate()
        tickHandler.sendEmptyMessageDelayed(WHAT_TICK, TIME_PER_FRAME)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        watchFacePaint.color = Color.GRAY
        watchFacePaint.style = Paint.Style.STROKE
        watchFacePaint.strokeWidth = 3.dp()

        handPaint.color = Color.RED
        handPaint.style = Paint.Style.STROKE
        handPaint.strokeWidth = 3.dp()
        handPaint.strokeCap = Paint.Cap.ROUND

        /* 获取当前系统时间 */
        calendar.timeInMillis = System.currentTimeMillis()
        // 时
        val hours = calendar.get(Calendar.HOUR) % 12
        // 分
        val minutes = calendar.get(Calendar.MINUTE)
        // 秒
        val seconds = calendar.get(Calendar.SECOND)
        // 角度
        val hourDegree = CA / 12 * hours
        val minuteDegree = CA / 60 * minutes
        val secondDegree = CA / 60 * seconds
        // 初始化角度
        currentHourHandDegree = hourDegree
        currentMinuteHandDegree = minuteDegree
        currentSecondHandDegree = secondDegree
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val sideLength = measuredWidth
        val centerX = sideLength / 2f
        val centerY = sideLength / 2f
        val radius = sideLength * 1f / 3

        /* 1. 绘制圆盘 */
        canvas.drawCircle(centerX, centerY, radius, watchFacePaint)
        canvas.drawPoint(centerX, centerY, watchFacePaint)

        val verticalMargin = sideLength / 2 - radius
        /* 2. 绘制刻度 */
        canvas.save()
        val lScaleLength = radius / 12 // 大刻度长度
        val sScaleLength = lScaleLength / 3 // 小刻度长度
        for (i in 0..59) {
            if (i % 5 == 0) { // 绘制大刻度
                canvas.drawLine(centerX, verticalMargin + lScaleLength, centerX, verticalMargin, watchFacePaint)
            } else { // 绘制小刻度
                canvas.drawLine(centerX, verticalMargin + sScaleLength, centerX, verticalMargin, watchFacePaint)
            }
            // 旋转画布，持续绘制，直到绘制完所有的刻度
            canvas.rotate(6f, centerX, centerY)
        }
        canvas.restore()

        /* 3. 绘制秒针 */
        handPaint.color = Color.RED
        currentSecondHandDegree += DEGREE_ONE_GRID / ONE_SECOND_IN_MILLIS * TIME_PER_FRAME
        currentSecondHandDegree %= CA
        canvas.save()
        canvas.rotate(currentSecondHandDegree, centerX, centerY)
        canvas.drawLine(centerX, centerY, centerX, verticalMargin + radius / 5, handPaint)
        canvas.restore()

        /* 4. 绘制分针 */
        handPaint.color = Color.GREEN
        currentMinuteHandDegree += DEGREE_ONE_GRID / ONE_MINUTE_IN_MILLIS * TIME_PER_FRAME
        currentMinuteHandDegree %= CA
        canvas.save()
        canvas.rotate(currentMinuteHandDegree, centerX, centerY)
        canvas.drawLine(centerX, centerY, centerX, verticalMargin + radius / 5 / 0.618f, handPaint)
        canvas.restore()

        /* 5. 绘制时针 */
        handPaint.color = Color.BLUE
        currentHourHandDegree += DEGREE_ONE_GRID / ONE_HOUR_IN_MILLIS * TIME_PER_FRAME
        currentHourHandDegree %= CA
        canvas.save()
        canvas.rotate(currentHourHandDegree, centerX, centerY)
        canvas.drawLine(centerX, centerY, centerX, verticalMargin + radius / 5 / 0.382f, handPaint)
        canvas.restore()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        tickHandler.sendEmptyMessage(WHAT_TICK)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        tickHandler.removeMessages(WHAT_TICK)
    }

}