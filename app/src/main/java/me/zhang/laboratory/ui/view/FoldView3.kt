package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.zhang.laboratory.R
import kotlin.math.sqrt

class FoldView3 : View {

    companion object {
        const val FOLD_COUNT: Int = 8
        const val TRANSPARENCY = 0.75F
        const val MIN_FACTOR = 0F
        const val FACTOR = 0.75F
        const val MAX_FACTOR = 1F
    }

    private val foldMatrices = arrayListOf<Matrix>()
    private val foldRect = RectF()

    private val rawBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_read)
    private val bitmap: Bitmap = Bitmap.createScaledBitmap(
        rawBitmap,
        rawBitmap.width / 2,
        rawBitmap.height / 2,
        false
    )
    private val foldWidth = (bitmap.width / FOLD_COUNT).toFloat()
    private var factor: Float = FACTOR
        set(value) {
            field = value
            setFoldMatrices()
            invalidate()
        }
    private val shadowPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lastX: Float = 0f
    private var shouldSlide: Boolean = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setFoldMatrices()
    }

    private fun setFoldMatrices() {
        val foldItemWidth = bitmap.width * factor / FOLD_COUNT
        val depth = sqrt(foldWidth * foldWidth - foldItemWidth * foldItemWidth) / 2

        for (i in 0 until FOLD_COUNT) {
            foldMatrices.add(Matrix())

            val isEven = i % 2 == 0

            val xStart = foldWidth * i
            val xEnd = foldWidth * (i + 1)

            val src = floatArrayOf(
                xStart, 0F,
                xEnd, 0F,
                xEnd, bitmap.height.toFloat(),
                xStart, bitmap.height.toFloat()
            )

            val dst = floatArrayOf(
                foldItemWidth * i,
                if (isEven) 0F else depth,
                foldItemWidth * (i + 1),
                if (isEven) depth else 0F,
                foldItemWidth * (i + 1),
                if (isEven) bitmap.height.toFloat() + depth else bitmap.height.toFloat(),
                foldItemWidth * i,
                if (isEven) bitmap.height.toFloat() else bitmap.height.toFloat() + depth
            )

            foldMatrices[i].setPolyToPoly(src, 0, dst, 0, src.size.shr(1))
        }

        shadowPaint.color = Color.argb((255 * (1 - factor) * TRANSPARENCY).toInt(), 0, 0, 0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until foldMatrices.size) {
            canvas.save()

            canvas.setMatrix(foldMatrices[i])

            foldRect.set(foldWidth * i, 0F, foldWidth * (i + 1), height.toFloat())
            canvas.clipRect(foldRect)

            canvas.drawBitmap(bitmap, 0F, 0F, null)

            if (i % 2 == 0) {
                shadowPaint.shader = null
            } else {
                shadowPaint.shader =
                    LinearGradient(
                        foldWidth * i,
                        0F,
                        foldWidth * (i + 1),
                        0F,
                        Color.BLACK,
                        Color.TRANSPARENT,
                        Shader.TileMode.CLAMP
                    )
            }
            canvas.drawRect(
                foldWidth * i,
                0F,
                foldWidth * (i + 1),
                bitmap.height.toFloat(),
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
                    (x > 0F && x < bitmap.width.toFloat()) && (y > 0F && y < bitmap.height.toFloat())
            }
            MotionEvent.ACTION_MOVE -> {
                if (shouldSlide) {
                    val x = event.x
                    val dx = x - lastX
                    val df = dx / bitmap.width

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