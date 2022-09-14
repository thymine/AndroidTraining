package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import me.zhang.laboratory.R
import kotlin.math.sqrt

class FoldLayout : LinearLayout {

    companion object {
        const val FOLD_COUNT: Int = 8
        const val TRANSPARENCY = 0.75F
        const val MIN_FACTOR = 0F
        const val FACTOR = 0.75F
        const val MAX_FACTOR = 1F
    }

    private val foldMatrices = arrayListOf<Matrix>()
    private val foldRect = RectF()

    private var foldWidth: Float = 0F
    private var foldHeight: Float = 0F
    private var factor: Float = FACTOR
        set(value) {
            field = value
            setFoldMatrices()
            invalidate()
        }
    private val shadowPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var linearGradient: LinearGradient
    private var lastX: Float = 0f
    private var shouldSlide: Boolean = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.FoldLayout)
        factor = ta?.getFloat(
            R.styleable.FoldLayout_factor,
            FACTOR
        ) ?: FACTOR
        ta?.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        foldWidth = (w / FOLD_COUNT).toFloat()
        foldHeight = h.toFloat()

        linearGradient = LinearGradient(
            0F, 0F,
            foldWidth, 0F,
            Color.BLACK,
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )

        setFoldMatrices()
    }

    private fun setFoldMatrices() {
        val foldItemWidth = foldWidth * factor
        val depth = sqrt(foldWidth * foldWidth - foldItemWidth * foldItemWidth) / 2

        for (i in 0 until FOLD_COUNT) {
            foldMatrices.add(Matrix())

            val isEven = i % 2 == 0

            val xStart = foldWidth * i
            val xEnd = foldWidth * (i + 1)

            val src = floatArrayOf(
                xStart, 0F,
                xEnd, 0F,
                xEnd, foldHeight,
                xStart, foldHeight
            )

            val dst = floatArrayOf(
                foldItemWidth * i, if (isEven) 0F else depth,
                foldItemWidth * (i + 1), if (isEven) depth else 0F,
                foldItemWidth * (i + 1), if (isEven) foldHeight else foldHeight - depth,
                foldItemWidth * i, if (isEven) foldHeight - depth else foldHeight
            )

            foldMatrices[i].setPolyToPoly(src, 0, dst, 0, src.size.shr(1))
        }

        shadowPaint.color = Color.argb((255 * (1 - factor) * TRANSPARENCY).toInt(), 0, 0, 0)
    }

    override fun dispatchDraw(canvas: Canvas) {
        for (i in 0 until foldMatrices.size) {
            canvas.save()

            canvas.setMatrix(foldMatrices[i])

            foldRect.set(foldWidth * i, 0F, foldWidth * (i + 1), height.toFloat())
            canvas.clipRect(foldRect)

            super.dispatchDraw(canvas)

            canvas.translate(foldWidth * i, 0F)
            shadowPaint.shader = if (i % 2 == 0) null else linearGradient
            canvas.drawRect(
                0F, 0F,
                foldWidth, foldHeight,
                shadowPaint
            )

            canvas.restore()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x

                val x = event.x
                val y = event.y

                shouldSlide =
                    (x > 0F && x < measuredWidth) && (y > 0F && y < measuredHeight)
            }
            MotionEvent.ACTION_MOVE -> {
                if (shouldSlide) {
                    val x = event.x
                    val dx = x - lastX
                    val df = dx / measuredWidth

                    val nf = factor + df
                    factor = if (nf < MIN_FACTOR) {
                        MIN_FACTOR
                    } else if (nf > MAX_FACTOR) {
                        MAX_FACTOR
                    } else {
                        nf
                    }
                    lastX = event.x
                }
            }
        }

        return true
    }

}