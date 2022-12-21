package me.zhang.laboratory.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ScrollView

class ScrollViewEx : ScrollView {

    private var downX: Float? = 0f
    private var downY: Float? = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercept = false

        val scrollableChild = findScrollableChild(this)

        val scrollableChildRect = scrollableChild?.getRect()

        scrollableChildRect?.offset(-scrollX, -scrollY)

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                intercept = false
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                intercept =
                    !(scrollableChildRect?.contains(downX?.toInt() ?: 0, downY?.toInt() ?: 0)
                        ?: false)
            }
            MotionEvent.ACTION_UP -> intercept = false
        }

        return intercept
    }

    private fun findScrollableChild(parent: ViewGroup): Scrollable? {
        var scrollable: Scrollable? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child is ViewGroup) {
                scrollable = findScrollableChild(child)
                if (scrollable != null) {
                    break
                }
            } else {
                if (child is Scrollable) {
                    scrollable = child
                    break
                }
            }
        }
        return scrollable
    }

}