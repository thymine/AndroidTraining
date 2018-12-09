package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.res.ResourcesCompat
import me.zhang.laboratory.R

class MutitouchView : View {

    companion object {
        const val INVALID_POINTER_ID = -1
    }

    private val scaleDetector: ScaleGestureDetector
    private val icon = ResourcesCompat.getDrawable(resources, R.mipmap.ic_launcher, null)

    init {
        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        // Create our ScaleGestureDetector
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    private var posX = 0f
    private var posY = 0f
    private var lastMotionX = 0f
    private var lastMotionY = 0f
    private var scaleFactor = 1.0f

    // The ‘active pointer’ is the one currently moving our object.
    private var activePointerId = INVALID_POINTER_ID

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(posX, posY)
        canvas.scale(scaleFactor, scaleFactor)
        icon?.draw(canvas)
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Let the ScaleGestureDetector inspect all events.
        scaleDetector.onTouchEvent(event)

        val action = event.action
        // ANDing it with MotionEvent.ACTION_MASK gives us the action constant
        when (action.and(MotionEvent.ACTION_MASK)) {
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

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!scaleDetector.isInProgress) {
                    // Calculate the distance moved
                    val dx = x - lastMotionX
                    val dy = y - lastMotionY

                    // Move the object
                    posX += dx
                    posY += dy

                    // Invalidate to request a redraw
                    invalidate()
                }

                // Remember this touch position for the next move event
                lastMotionX = x
                lastMotionY = y
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                activePointerId = INVALID_POINTER_ID
            }
            MotionEvent.ACTION_POINTER_UP -> {
                // Extract the index of the pointer that left the touch sensor
                val pointerIndex =
                        action
                                //  ANDing it with ACTION_POINTER_INDEX_MASK gives us the index of the pointer that went up or down
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

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f))

            invalidate()
            return true
        }
    }

}