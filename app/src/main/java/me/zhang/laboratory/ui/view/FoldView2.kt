package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import me.zhang.laboratory.R
import me.zhang.laboratory.utils.dp
import kotlin.math.sqrt

class FoldView2 : View {

    companion object {
        val TRANS_HEIGHT: Float = 96.dp()
        const val FOLD_COUNT: Int = 8
        const val FACTOR = 0.8F
    }

    private val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_read)
    private val foldMatrix = android.graphics.Matrix()
    private val foldWidth = (bitmap.width / FOLD_COUNT).toFloat()
    private val foldItemWidth = (bitmap.width * FACTOR / FOLD_COUNT).toFloat()
    private val depth = sqrt(foldWidth * foldWidth - foldItemWidth * foldItemWidth)
    private val foldRect = RectF(foldWidth, 0F, foldWidth * 2, bitmap.height.toFloat())

    private val src = floatArrayOf(
            foldWidth, 0F,
            foldWidth * 2, 0F,
            foldWidth * 2, bitmap.height.toFloat(),
            foldWidth, bitmap.height.toFloat()
    )
    private val dst = floatArrayOf(
            foldItemWidth, depth,
            foldItemWidth * 2, 0F,
            foldItemWidth * 2, bitmap.height.toFloat(),
            foldItemWidth, bitmap.height.toFloat() + depth
    )

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        foldMatrix.setPolyToPoly(src, 0, dst, 0, src.size.shr(1))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.setMatrix(foldMatrix)
        canvas.clipRect(foldRect)
        canvas.drawBitmap(bitmap, 0F, 0F, null)
        canvas.restore()
    }

}