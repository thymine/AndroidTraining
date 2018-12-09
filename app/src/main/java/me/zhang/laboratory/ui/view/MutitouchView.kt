package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import me.zhang.laboratory.R

class MutitouchView : View {

    val icon = ResourcesCompat.getDrawable(resources, R.mipmap.ic_launcher, null)

    init {
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
    }

    var posX = 0F
    var posY = 0F
    var lastMotionX = 0F
    var lastMotionY = 0F

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(posX, posY)
        icon?.draw(canvas)
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y

                lastMotionX = x
                lastMotionY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y

                val dx = x - lastMotionX
                val dy = y - lastMotionY

                posX += dx
                posY += dy

                lastMotionX = x
                lastMotionY = y

                invalidate()
            }
        }
        return true
    }

}