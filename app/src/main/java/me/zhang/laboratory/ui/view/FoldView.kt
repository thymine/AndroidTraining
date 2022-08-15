package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import me.zhang.laboratory.R
import me.zhang.laboratory.utils.dp

class FoldView : View {

    companion object {
        val TRANS_HEIGHT: Float = 96.dp()
        const val FOLD_COUNT: Int = 8
    }

    private val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_read)
    private val foldMatrix = android.graphics.Matrix()
    private val src = floatArrayOf(
            0F, 0F,
            bitmap.width.toFloat(), 0F,
            bitmap.width.toFloat(), bitmap.height.toFloat(),
            0F, bitmap.height.toFloat()
    )
    private val dst = floatArrayOf(
            0F, 0F,
            bitmap.width.toFloat(), TRANS_HEIGHT,
            bitmap.width.toFloat(), bitmap.height + TRANS_HEIGHT,
            0F, bitmap.height.toFloat()
    )
    private val foldWidth = bitmap.width / FOLD_COUNT
    private val foldRect = Rect(0, 0, foldWidth, bitmap.height)

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