package me.zhang.workbench.ui

import android.content.Context
import android.graphics.*
import android.graphics.Canvas.ALL_SAVE_FLAG
import android.util.AttributeSet
import android.view.View


/**
 * Created by zhangxiangdong on 2018/3/30 15:51.
 */
class PorterDuffXferView : View {

    private val dstBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)!!
    private val dstCanvas = Canvas(dstBitmap)
    private val dstPaint = Paint()

    private val srcBitmap = dstBitmap.copy(Bitmap.Config.ARGB_8888, true)!!
    private val srcCanvas = Canvas(srcBitmap)
    private val srcPaint = Paint()

    private val bitmap3 = Bitmap.createBitmap(450, 450, Bitmap.Config.ARGB_8888)!!
    private val canvas3 = Canvas(bitmap3)
    private val paint3 = Paint()

    private val xorXfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setBackgroundColor(Color.parseColor("#FFCCCC"))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dstPaint.color = Color.GRAY
        dstCanvas.drawCircle(150f, 150f, 150f, dstPaint)

        srcPaint.color = Color.GREEN
        srcCanvas.drawRect(0f, 0f, 300f, 300f, srcPaint)

        val saveLayerCount = canvas3.saveLayer(0f, 0f, 450f, 450f, null, ALL_SAVE_FLAG)
        canvas3.drawBitmap(dstBitmap, 0f, 0f, null)

        paint3.xfermode = xorXfermode
        canvas3.drawBitmap(srcBitmap, 150f, 150f, paint3)

        paint3.xfermode = null
        canvas3.restoreToCount(saveLayerCount)

        canvas.drawBitmap(bitmap3, 0f, 0f, null)
    }

}