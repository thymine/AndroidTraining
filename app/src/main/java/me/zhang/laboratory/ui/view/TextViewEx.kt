package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.Rect
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.view.MotionEvent


class TextViewEx : androidx.appcompat.widget.AppCompatTextView, Scrollable {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        movementMethod = ScrollingMovementMethod()
    }

    override fun getRect(): Rect {
        return Rect(left, top, right, bottom)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }

        return super.dispatchTouchEvent(event)
    }

}