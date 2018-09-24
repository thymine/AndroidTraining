package me.zhang.workbench.ui

import android.content.Context
import android.graphics.*
import android.graphics.Matrix.MTRANS_X
import android.graphics.Matrix.MTRANS_Y
import android.util.AttributeSet
import android.util.Log
import android.view.View
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp
import java.util.*

/**
 * Created by zhangxiangdong on 2018/3/30 15:51.
 */
class PorterDuffXferView : View {

    private val srcBitmap = BitmapFactory.decodeResource(resources, R.drawable.composite_src)
    private val dstBitmap = BitmapFactory.decodeResource(resources, R.drawable.composite_dst)
    private val bitmapPaint = Paint()
    private val textPaint = Paint()
    private val modes = ArrayList<PorterDuffXfermode>()
    private val bitmapMatrix = Matrix()
    private val values = FloatArray(9)

    init {
        PorterDuff.Mode.values().forEach {
            modes.add(PorterDuffXfermode(it))
        }

        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.style = Paint.Style.FILL
        textPaint.color = Color.YELLOW
        textPaint.textSize = 12.dp()
    }

    companion object {
        const val LOG_TAG = "PorterDuffXferView"
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var x = 0
        var y = 0
        modes.forEach { mode ->
            bitmapMatrix.setScale(.3f, .3f)

            val drawingBound = srcBitmap.width * .31f
            if ((x + 1) * drawingBound /* 绘制区域方块右上角 */ >= measuredWidth) {
                x = 0
                y++
            }
            Log.d(LOG_TAG, "($x, $y)")
            bitmapMatrix.postTranslate(x++ * drawingBound, y * drawingBound)

            canvas.drawBitmap(dstBitmap, bitmapMatrix, bitmapPaint)
            bitmapPaint.xfermode = mode
            canvas.drawBitmap(srcBitmap, bitmapMatrix, bitmapPaint)

            bitmapMatrix.getValues(values)
            val margin = 3.dp()
            canvas.drawText(
                    PorterDuff.Mode.values()[modes.indexOf(mode)].name,
                    values[MTRANS_X] + margin,
                    values[MTRANS_Y] + Math.abs(textPaint.fontMetrics.top) + margin,
                    textPaint
            )
        }
    }

}