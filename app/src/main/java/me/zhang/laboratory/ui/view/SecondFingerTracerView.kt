package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.laboratory.utils.dp

class SecondFingerTracerView : View {

    private val pointF = PointF()
    private val paint = Paint()
    private var hasSecondFinger: Boolean = false

    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val actionIndex = event.actionIndex
        when (event.actionMasked) {
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.getPointerId(actionIndex) == 1) {
                    hasSecondFinger = true
                    pointF.set(event.getX(actionIndex), event.getY(actionIndex))
                }
            }
            MotionEvent.ACTION_MOVE -> {
                try {
                    if (hasSecondFinger) {
                        val secondPointerIndex = event.findPointerIndex(1)
                        pointF.set(
                            event.getX(secondPointerIndex),
                            event.getY(secondPointerIndex)
                        )
                    }
                } catch (_: Exception) {
                    hasSecondFinger = false
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (event.getPointerId(actionIndex) == 1) {
                    hasSecondFinger = false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                hasSecondFinger = false
            }
        }

        invalidate()

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.RED)
        if (hasSecondFinger) {
            canvas.drawCircle(
                pointF.x,
                pointF.y,
                32.dp(),
                paint
            )
        }
    }

}