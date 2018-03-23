package me.zhang.workbench.ui.paint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import me.zhang.workbench.utils.dp
import java.io.FileOutputStream

/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintView : View {

    companion object {
        const val LOG_TAG = "PaintView"
    }

    private var shapePainter: ShapePainter = LinePainter(this)
    internal val paint = Paint()
    private var bitmapBuffer: Bitmap? = null
    private var bitmapCanvas: Canvas? = null

    private var shouldClearCanvas: Boolean? = null

    fun useThisPainter(painter: ShapePainter) {
        shapePainter = painter
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 3.dp()
        paint.color = Color.RED

        setBackgroundColor(Color.BLACK)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (bitmapBuffer == null) {
            bitmapBuffer = createBitmap(measuredWidth, measuredHeight, ARGB_8888)
            bitmapCanvas = Canvas(bitmapBuffer)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (bitmapBuffer != null) {
            shapePainter.onDraw(canvas, bitmapBuffer!!)
        }

        // 根据shouldClearCanvas字段判断是否应该清空画布内容
        shouldClearCanvas?.let {
            if (it) {
                bitmapBuffer?.eraseColor(Color.TRANSPARENT)
                canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
                shouldClearCanvas = false
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return bitmapCanvas?.let { shapePainter.onTouchEvent(event, bitmapCanvas!!) }
                ?: false
    }

    fun changePaintColor(selectedColor: Int) {
        paint.color = selectedColor
    }

    fun clearCanvas() {
        bitmapCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        shouldClearCanvas = true
        invalidate()
    }

    /**
     * 保存绘制内容到磁盘
     *
     * @return 保存的文件绝对路径
     */
    fun captureCanvas(): String? {
        val fileName = "paint_${System.currentTimeMillis()}"
        val filePath = "${Environment.getExternalStorageDirectory().absolutePath}/$fileName.jpg"
        Log.i(LOG_TAG, filePath)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(filePath)
            bitmapBuffer?.compress(Bitmap.CompressFormat.JPEG, 50, out)
            out.flush()
            return filePath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

}