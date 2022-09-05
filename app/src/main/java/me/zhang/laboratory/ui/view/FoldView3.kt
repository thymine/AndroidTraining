package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.zhang.laboratory.R
import kotlin.math.sqrt

class FoldView3 : View {

    companion object {
        const val FOLD_COUNT: Int = 8
        const val FACTOR = 0.8F
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
    var factor: Float = FACTOR
        set(value) {
            field = value
            setFoldMatrices()
            invalidate()
        }

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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until foldMatrices.size) {
            canvas.save()

            canvas.setMatrix(foldMatrices[i])

            foldRect.set(foldWidth * i, 0F, foldWidth * (i + 1), height.toFloat())
            canvas.clipRect(foldRect)

            canvas.drawBitmap(bitmap, 0F, 0F, null)

            canvas.restore()
        }
    }

}