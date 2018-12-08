package me.zhang.laboratory.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.R
import me.zhang.laboratory.ui.Const.Companion.ACTION_MAP
import me.zhang.laboratory.ui.Const.Companion.LOG_TAG

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

class TouchEventActivity : AppCompatActivity() {

    var lastAction: Int = MotionEvent.ACTION_DOWN

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
        return handled
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(LOG_TAG, "Layout.onTouchEvent: " + ACTION_MAP[event.action])
        val handled = super.onTouchEvent(event)
        Log.d(LOG_TAG, "Layout.onTouchEvent, $handled")
        return handled
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}

class MyView : View {

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
        return handled
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}