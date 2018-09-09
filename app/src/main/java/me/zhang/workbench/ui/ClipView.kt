package me.zhang.workbench.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.R
import java.util.*

/**
 * Created by zhangxiangdong on 2018/3/16.
 */
class ClipView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        setOnClickListener({
            when (isAnimating) {
                true -> stop()
                false -> start()
            }
        })
    }

    constructor(context: Context) : this(context, null)

    private var timer: Timer? = null
    private val photo3 = BitmapFactory.decodeResource(resources, R.drawable.photo3)
    private val photo3Rect = Rect(0, 0, photo3.width * 3 / 5, photo3.height * 3 / 5)
    private val photo3Path = Path()

    private val boom = BitmapFactory.decodeResource(resources, R.drawable.boom)
    private val boomWidth = boom.width
    private val frameWidth = boomWidth / 7
    private val boomRect = Rect(0, 0, frameWidth, boom.height)
    private var currentFrame = 0
    private var isAnimating = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
                getDefaultSize(suggestedMinimumHeight, MeasureSpec.makeMeasureSpec(size * 2, mode)))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        val photo3Height = photo3.height
        val verticalMargin = photo3Height * 0.2f

        canvas.save()
        canvas.drawBitmap(photo3, 0f, 0f, null)
        canvas.restore()

        // 使用clip
        canvas.translate(0f, photo3Height + verticalMargin)
        canvas.save()
        canvas.clipRect(photo3Rect)
        photo3Path.addCircle(photo3.width * 3 / 5f, photo3.height * 3 / 5f, photo3.height / 3f, Path.Direction.CCW)
        @Suppress("DEPRECATION")
        canvas.clipPath(photo3Path, Region.Op.UNION)
        canvas.drawBitmap(photo3, 0f, 0f, null)
        canvas.restore()

        // 绘制动画效果
        canvas.save()
        canvas.translate(0f, photo3Height + verticalMargin)
        canvas.clipRect(boomRect)
        canvas.drawBitmap(boom, -1f * currentFrame++ * frameWidth, 0f, null)
        if (currentFrame == 7) {
            currentFrame = 0
        }
        canvas.restore()
    }

    private fun start() {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                postInvalidate()
            }
        }, 0, 100)
        isAnimating = true
    }

    private fun stop() {
        timer?.cancel()
        isAnimating = false
    }

}