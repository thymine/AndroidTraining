package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.ImageView
import me.zhang.laboratory.R
import me.zhang.laboratory.utils.toast
import kotlin.math.abs

class FloatingTrackerBall : FrameLayout, View.OnClickListener {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        inflate(context, R.layout.layout_floating_tracker_ball, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<ImageView>(R.id.iv_start).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_top).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_end).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_bottom).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_center).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_start -> "←".toast(context)
            R.id.iv_top -> "↑".toast(context)
            R.id.iv_end -> "→".toast(context)
            R.id.iv_bottom -> "↓".toast(context)
            R.id.iv_center -> "□".toast(context)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.x
                lastY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - lastX
                val dy = ev.y - lastY
                if ((abs(dx) >= ViewConfiguration.get(context).scaledTouchSlop)
                    || (abs(dy) >= ViewConfiguration.get(context).scaledTouchSlop)
                ) {
                    return true
                }
            }
        }
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastX
                val dy = event.y - lastY

                translationX += dx.toInt()
                translationY += dy.toInt()

                return true
            }
        }
        return super.onTouchEvent(event)
    }

}