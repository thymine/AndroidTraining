package me.zhang.laboratory.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Scroller

class ScrollerLayout : LinearLayout {

    companion object {
        const val DEFAULT_DURATION = 400
    }

    private val scroller: Scroller

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        scroller = Scroller(context)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

    fun smoothScrollBy(x: Int, y: Int) {
        scroller.startScroll(scrollX, scrollY, x, y, DEFAULT_DURATION)
        postInvalidate()
    }

}