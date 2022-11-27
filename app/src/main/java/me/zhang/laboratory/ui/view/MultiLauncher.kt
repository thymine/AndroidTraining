package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Scroller

class MultiLauncher : ViewGroup {

    companion object {
        const val TOUCH_STATE_STOP = 0
        const val TOUCH_STATE_FLING = 1
        const val SNAP_VELOCITY = 200
    }

    private val scroller: Scroller;
    private var touchState: Int = TOUCH_STATE_STOP
    private var touchSlop: Int = 0
    private var lastMotionX: Float = 0f
    private var currScreen: Int = 0
    private var velocityTracker: VelocityTracker? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)

        var width = 0;
        if (mode == MeasureSpec.AT_MOST) {
            throw IllegalArgumentException()
        } else {
            width = size
        }
        return width * childCount
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)

        var height = 0
        if (mode == MeasureSpec.AT_MOST) {
            throw IllegalArgumentException()
        } else {
            height = size
        }
        return height
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val n = childCount
        val w = (r - l) / n
        val h = b - t

        for (i in 0 until n) {
            val child = getChildAt(i)
            val left = i * w
            val top = 0;
            val right = (i + 1) * w
            val bottom = h

            child.layout(left, top, right, bottom)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        val x = ev.x
        val y = ev.y

        if (action == MotionEvent.ACTION_MOVE && touchState == TOUCH_STATE_STOP) {
            return true
        }

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                lastMotionX = x
                touchState = if (scroller.isFinished) TOUCH_STATE_STOP else TOUCH_STATE_FLING
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = Math.abs(x - lastMotionX)
                if (dx > touchSlop) {
                    touchState = TOUCH_STATE_FLING
                }
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                touchState = TOUCH_STATE_STOP
            }
        }

        return touchState != TOUCH_STATE_STOP
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker?.addMovement(event)
        val action = event.action
        val x = event.x
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
                lastMotionX = x
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = lastMotionX - x
                scrollBy(dx.toInt(), 0)
                lastMotionX = x
            }
            MotionEvent.ACTION_UP -> {
                val vt = velocityTracker
                vt?.computeCurrentVelocity(1000)
                val velocityX = vt?.xVelocity ?: 0f
                if (velocityX > SNAP_VELOCITY && currScreen > 0) {
                    moveToPrevious()
                } else if (velocityX < -SNAP_VELOCITY && currScreen < childCount - 1) {
                    moveToNext()
                } else {
                    moveToDestination()
                }

                velocityTracker?.clear()
                velocityTracker?.recycle()
                velocityTracker = null

                touchState = TOUCH_STATE_STOP
            }
            MotionEvent.ACTION_CANCEL -> touchState = TOUCH_STATE_STOP
        }

        return true
    }

    private fun moveToPrevious() {
        moveToScreen(currScreen - 1)
    }

    private fun moveToNext() {
        moveToScreen(currScreen + 1)
    }

    private fun moveToDestination() {
        val splitWidth = width / childCount
        val toScreen = (scrollX + splitWidth / 2) / splitWidth
        moveToScreen(toScreen)
    }

    private fun moveToScreen(whichScreen: Int) {
        currScreen = whichScreen
        if (currScreen > childCount - 1) {
            currScreen = childCount - 1
        } else if (currScreen < 0) {
            currScreen = 0
        }

        val scrollX = scrollX
        val splitWidth = width / childCount
        val dx = currScreen * splitWidth - scrollX
        scroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx))

        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, 0)
            postInvalidate()
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.scroller = Scroller(context)
        this.touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

}