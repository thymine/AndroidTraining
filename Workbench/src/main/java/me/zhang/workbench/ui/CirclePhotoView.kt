package me.zhang.workbench.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.R

/**
 * Created by zhangxiangdong on 2018/4/26 11:47.
 */
class CirclePhotoView : View {

    companion object {
        const val FACTOR_SCALE = 2
    }

    private val catBitmapMatrix = Matrix()
    private val circleMaskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val catBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.androidpartyx)

    private val circleMaskBitmap: Bitmap
    private val circleMaskCanvas: Canvas
    private val dstIn = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    init {
        val w = Math.min(catBitmap.width, catBitmap.height) / FACTOR_SCALE
        circleMaskBitmap = Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888)
        circleMaskCanvas = Canvas(circleMaskBitmap)

        val r = (w / 2).toFloat()
        circleMaskCanvas.drawCircle(r, r, r, circleMaskPaint)
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val circleMaskWidth = circleMaskBitmap.width * 1f
        val catWidth = catBitmap.width * 1f / 2

        val circleMaskLeft = (catWidth - circleMaskWidth) / 2f

        @Suppress("DEPRECATION")
        val layer = canvas.saveLayer(circleMaskLeft, 0f, circleMaskLeft + circleMaskWidth, circleMaskLeft + circleMaskWidth, null, Canvas.ALL_SAVE_FLAG)

        catBitmapMatrix.setTranslate(0f, 0f)
        catBitmapMatrix.postScale(1f / FACTOR_SCALE, 1f / FACTOR_SCALE)
        canvas.drawBitmap(catBitmap, catBitmapMatrix, null)
        circleMaskPaint.xfermode = dstIn
        canvas.drawBitmap(circleMaskBitmap, circleMaskLeft, 0f, circleMaskPaint)

        canvas.restoreToCount(layer)
    }

}