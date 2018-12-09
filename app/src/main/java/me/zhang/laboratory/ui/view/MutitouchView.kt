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

    companion object {
        const val INVALID_POINTER_ID = -1
    }

    val icon = ResourcesCompat.getDrawable(resources, R.mipmap.ic_launcher, null)

    init {
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
    }

    var posX = 0F
    var posY = 0F
    var lastMotionX = 0F
    var lastMotionY = 0F

    // The ‘active pointer’ is the one currently moving our object.
    var activePointerId = INVALID_POINTER_ID

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
        when (action.and(MotionEvent.ACTION_MASK)) { // TODO Why?
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y

                // Remember where we started
                lastMotionX = x
                lastMotionY = y

                // Save the ID of this pointer
                activePointerId = event.getPointerId(0)
            }
            MotionEvent.ACTION_MOVE -> {
                // Find the index of the active pointer and fetch its position
                val pointerIndex = event.findPointerIndex(activePointerId)

                val x = event.getX(pointerIndex)
                val y = event.getY(pointerIndex)

                // Calculate the distance moved
                val dx = x - lastMotionX
                val dy = y - lastMotionY

                // Move the object
                posX += dx
                posY += dy

                // Remember this touch position for the next move event
                lastMotionX = x
                lastMotionY = y

                // Invalidate to request a redraw
                invalidate()
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                activePointerId = INVALID_POINTER_ID
            }
            MotionEvent.ACTION_POINTER_UP -> {
                // Extract the index of the pointer that left the touch sensor
                val pointerIndex =
                        action
                                .and(MotionEvent.ACTION_POINTER_INDEX_MASK)
                                .shr(MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                val pointerId = event.getPointerId(pointerIndex)
                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerId == 0) 1 else 0
                    lastMotionX = event.getX(newPointerIndex)
                    lastMotionY = event.getY(newPointerIndex)
                    activePointerId = event.getPointerId(newPointerIndex)
                }
            }
        }
        return true
    }

}