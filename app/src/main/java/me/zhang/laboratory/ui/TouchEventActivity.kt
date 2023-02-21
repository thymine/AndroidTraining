package me.zhang.laboratory.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import me.zhang.laboratory.R
import me.zhang.laboratory.ui.Const.Companion.ACTION_MAP
import me.zhang.laboratory.ui.Const.Companion.LOG_TAG
import kotlin.math.abs

class Const {
    companion object {
        const val LOG_TAG = "TouchEventActivity"
        val ACTION_MAP = mutableMapOf<Int, String>()

        init {
            ACTION_MAP[MotionEvent.ACTION_DOWN] = "ACTION_DOWN"
            ACTION_MAP[MotionEvent.ACTION_MOVE] = "ACTION_MOVE"
            ACTION_MAP[MotionEvent.ACTION_UP] = "ACTION_UP"
            ACTION_MAP[MotionEvent.ACTION_CANCEL] = "ACTION_CANCEL"
        }
    }
}

class TouchEventActivity : BaseActivity() {

    private var lastAction: Int = MotionEvent.ACTION_DOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action

        if (action != lastAction) {
            Log.d(LOG_TAG, "\n----------------\n")
            lastAction = action
        }

        Log.d(LOG_TAG, "Activity.dispatchTouchEvent: " + ACTION_MAP[action])
        val handled = super.dispatchTouchEvent(ev)
        Log.d(LOG_TAG, "Activity.dispatchTouchEvent, $handled")
        return handled
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(LOG_TAG, "Activity.onTouchEvent: " + ACTION_MAP[event.action])
        val handled = super.onTouchEvent(event)
        Log.d(LOG_TAG, "Activity.onTouchEvent, $handled")
        return handled
    }

}

class MyLayout : FrameLayout {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.d(LOG_TAG, "Layout.dispatchTouchEvent: " + ACTION_MAP[ev.action])
        val handled = super.dispatchTouchEvent(ev)
        Log.d(LOG_TAG, "Layout.dispatchTouchEvent, $handled")
        return handled
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.d(LOG_TAG, "Layout.onInterceptTouchEvent: " + ACTION_MAP[ev.action])
        val handled = super.onInterceptTouchEvent(ev)
        Log.d(LOG_TAG, "Layout.onInterceptTouchEvent, $handled")

        when (ev.action) {
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
                    val childView = getChildAt(0)
                    val realLeft = childView.left + childView.translationX
                    val realRight = realLeft + childView.width
                    val realTop = childView.top + childView.translationY
                    val realBottom = realTop + childView.height
                    if (!(ev.x in realLeft..realRight && ev.y in realTop..realBottom)) {
                        return true
                    }
                }
            }
        }

        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(LOG_TAG, "Layout.onTouchEvent: " + ACTION_MAP[event.action])
        val handled = super.onTouchEvent(event)
        Log.d(LOG_TAG, "Layout.onTouchEvent, $handled")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastX
                val dy = event.y - lastY

                translationX += dx
                translationY += dy
            }
        }

        return true
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

}

class MyView : View {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.d(LOG_TAG, "View.dispatchTouchEvent: " + ACTION_MAP[event.action])
        val handled = super.dispatchTouchEvent(event)
        Log.d(LOG_TAG, "View.dispatchTouchEvent, $handled")
        return handled
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(LOG_TAG, "View.onTouchEvent: " + ACTION_MAP[event.action])
        val handled = super.onTouchEvent(event)
        Log.d(LOG_TAG, "View.onTouchEvent, $handled")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastX
                val dy = event.y - lastY

                translationX += dx
                translationY += dy
            }
        }

        return true
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

}