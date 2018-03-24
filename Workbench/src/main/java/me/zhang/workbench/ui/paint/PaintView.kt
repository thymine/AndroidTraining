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
import java.util.*

/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintView : View, PaintContract.PaintPresenter {

    companion object {
        const val LOG_TAG = "PaintUi"
    }

    private var shapePainter: ShapePainter = LinePainter(this)
    private val paint = Paint()
    private var bitmapBuffer: Bitmap? = null
    private var bitmapCanvas: Canvas? = null
    private var shouldClearCanvas: Boolean? = null
    private var paintUi: PaintContract.PaintUi? = null
    private val drawingShapes = Stack<Shape>()
    private val cachedShapes = Stack<Shape>()
    private var visualTempShape: Shape? = null

    override fun setVisualTempShape(tempShape: Shape) {
        visualTempShape = tempShape
    }

    override fun redraw() {
        invalidate()
    }

    override fun getPaint(): Paint {
        return paint
    }

    override fun attachPaintUi(paintUi: PaintContract.PaintUi) {
        this.paintUi = paintUi
    }

    override fun changePainter(painter: ShapePainter) {
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

        bitmapCanvas?.let {
            // 先清空原绘制内容
            bitmapCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            for (shape in drawingShapes) { // todo 需要性能优化
                // 绘制所有的待绘制的路径
                shape.draw(it, paint)
            }
        }
        // 将内容绘制到视图上
        canvas.drawBitmap(bitmapBuffer, 0f, 0f, null)
        // 绘制绘图过程的临时路径，比如，绘制一个长方形，显示拖拽过程中的图像
        visualTempShape?.draw(canvas, paint)

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

    override fun changePaintColor(selectedColor: Int) {
        paint.color = selectedColor
    }

    override fun clearCanvas() {
        drawingShapes.clear()
        cachedShapes.clear()
        updateUndoRedoButtonStatus()
        bitmapCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        shouldClearCanvas = true
        redraw()
    }

    /**
     * 保存绘制内容到磁盘
     *
     * @return 保存的文件绝对路径
     */
    override fun captureCanvas(): String? {
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

    override fun undoPainting() {
        if (noDrawingPaths()) {
            throw IllegalStateException("No undo path anymore!")
        }
        cachedShapes.push(drawingShapes.pop())
        redraw()
        updateUndoRedoButtonStatus()
    }

    private fun noDrawingPaths() = drawingShapes.size == 0

    override fun addDrawingShape(shape: Shape) {
        drawingShapes.push(shape)
        updateUndoRedoButtonStatus()
    }

    override fun redoPainting() {
        if (noCachedPaths()) {
            throw IllegalStateException("No redo path anymore!")
        }
        drawingShapes.push(cachedShapes.pop())
        redraw()
        updateUndoRedoButtonStatus()
    }

    private fun updateUndoRedoButtonStatus() {
        if (paintUi == null) {
            throwPaintUiNotAttached()
        } else {
            paintUi!!.onUndoButtonEnabled(!noDrawingPaths())
            paintUi!!.onRedoButtonEnabled(!noCachedPaths())
        }
    }

    private fun throwPaintUiNotAttached() {
        throw IllegalStateException("PaintUi not attached yet!")
    }

    private fun noCachedPaths() = cachedShapes.size == 0

}