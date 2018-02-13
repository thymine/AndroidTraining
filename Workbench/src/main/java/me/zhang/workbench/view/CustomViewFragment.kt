package me.zhang.workbench.view

import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.zhang.workbench.R
import me.zhang.workbench.utils.UiUtils
import me.zhang.workbench.utils.toPixel
import me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE

/**
 * Created by Zhang on 8/19/2017 6:52 PM.
 */
class CustomViewFragment : Fragment() {

    private var mTextImage: ImageView? = null
    private var mCustomImage: ImageView? = null
    private var mGraphicsImage: ImageView? = null
    private var mBitmapImage: ImageView? = null

    companion object {
        fun newInstance(title: String): CustomViewFragment {
            val args = Bundle()
            args.putString(FRAGMENT_TITLE, title)

            val fragment = CustomViewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_custom_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCustomImage = view!!.findViewById(R.id.custom_image)
        mCustomImage!!.setImageBitmap(getDrawnBitmap())

        mBitmapImage = view.findViewById(R.id.bitmap_image)
        mBitmapImage?.setImageBitmap(getScaleBitmap())

        mGraphicsImage = view.findViewById(R.id.graphics_image)
        mGraphicsImage?.setImageBitmap(getGraphicsBitmap())

        mTextImage = view.findViewById(R.id.text_image)
        mTextImage?.setImageBitmap(getTextBitmap())
    }

    private fun getDrawnBitmap(): Bitmap {
        val width = 1480
        val height = 880
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)

        val rPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        rPaint.style = Paint.Style.FILL
        rPaint.color = Color.BLACK

        val rectF = RectF(100F, 100F, 300F, 300F)
        val rPath = Path()
        rPath.addRect(rectF, Path.Direction.CCW)

        val cPath = Path()
        cPath.addCircle(300F, 300F, 100F, Path.Direction.CCW)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rPath.op(cPath, Path.Op.XOR)
        }
        canvas.drawPath(rPath, rPaint)

        return bitmap
    }

    private fun getScaleBitmap(): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(800, 500, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmapBuffer)

        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        /* 从bitmap中抠出一块大小为src的区域，绘制到dst区域 */
        val width = bitmap.width
        val height = bitmap.height
        val src = Rect(width / 2, height / 2, width, height)
        val dst = Rect(width, 0, width * 4, height * 3)
        canvas.drawBitmap(bitmap, src, dst, null)
        return bitmapBuffer
    }

    private fun getGraphicsBitmap(): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(1600, 600, Bitmap.Config.ARGB_4444)

        val canvas = Canvas(bitmapBuffer)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        //region Draw dots
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
        paint.strokeWidth = UiUtils.convertDpToPixel(6f, context)

        canvas.drawPoint(20f, 30f, paint)

        paint.color = Color.GREEN
        paint.strokeWidth = UiUtils.convertDpToPixel(20f, context)

        val pts = floatArrayOf(140f, 50f, 260f, 130f, 380f, 160f)
        canvas.drawPoints(pts, paint)
        //endregion

        //region Draw lines
        paint.strokeWidth = UiUtils.convertDpToPixel(3f, context)
        paint.color = Color.RED
        canvas.drawLine(400f, 200f, 700f, 300f, paint)

        val lpts = floatArrayOf(300f, 200f, 350f, 250f, 600f, 100f, 700f, 460f)
        canvas.drawLines(lpts, paint)
        //endregion

        //region Draw rectangles
        paint.color = Color.YELLOW
        val rect = Rect(100, 300, 400, 500)
        canvas.drawRect(rect, paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = UiUtils.convertDpToPixel(1f, context)
        paint.color = Color.CYAN
        rect.set(150, 350, 350, 450)
        val rectF = RectF(rect)
        val rX = UiUtils.convertDpToPixel(6f, context)
        val rY = UiUtils.convertDpToPixel(6f, context)
        canvas.drawRoundRect(rectF, rX, rY, paint)
        //endregion

        //region Draw circles
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas.drawCircle(250f, 400f, 30f, paint)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        canvas.drawOval(rectF, paint)

        paint.color = Color.LTGRAY
        paint.style = Paint.Style.FILL
        canvas.drawArc(rectF, -15f, -30f, true, paint)

        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3.toPixel(context)
        canvas.drawArc(rectF, 135f, 30f, false, paint)
        //endregion

        //region Draw paths
        val path = Path()
        paint.strokeWidth = 1.toPixel(context)
        paint.color = Color.GREEN

        path.moveTo(800f, 100f)
        path.rLineTo(200f, 0f)
        path.rLineTo(-100f, Math.sqrt(Math.pow(200.0, 2.0) - Math.pow(100.0, 2.0)).toFloat())
        path.lineTo(800f, 100f)
        path.close()

        canvas.drawPath(path, paint)

        paint.color = Color.RED
//        path.rewind()
        path.reset()
        rectF.set(800F, 320F, 1000F, 500F)
        path.addRect(rectF, Path.Direction.CCW)
        canvas.drawPath(path, paint)
        //endregion

        //region Draw bezier curves
        paint.color = Color.BLUE
        path.reset()
        path.moveTo(1000f, 120f)
        path.quadTo(1300f, 240f, 1100f, 380f)
        canvas.drawPath(path, paint)

        paint.strokeWidth = 4.toPixel(context)
        paint.color = Color.GREEN
        canvas.drawPoints(floatArrayOf(1000f, 120f, 1300f, 240f, 1100f, 380f), paint)

        path.reset()
        path.moveTo(1100f, 380f)
        path.rQuadTo(600f, 200f, 100f, 600f)
        paint.strokeWidth = 1.toPixel(context)
        paint.color = Color.RED
        canvas.drawPath(path, paint)

        path.reset()
        path.moveTo(1100f, 400f)
        val rf = RectF(1100f, 400f, 1300f, 500f)
        path.arcTo(rf, 0f, -90f, false)
        paint.color = Color.GREEN
        canvas.drawRect(rf, paint)
        canvas.drawOval(rf, paint)
        paint.color = Color.BLACK
        canvas.drawPath(path, paint)
        //endregion

        return bitmapBuffer
    }

    private fun getTextBitmap(): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(1600, 800, Bitmap.Config.ARGB_4444)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = 18.toPixel(context)
        paint.color = Color.BLACK

        val canvas = Canvas(bitmapBuffer)

        val text = "Android Lab"

        canvas.drawText(text, 100F, 100F, paint)

        canvas.drawText(text, 0, 7, 100F, 200F, paint)

        canvas.drawText(text.toCharArray(), 8, 3, 100F, 300F, paint)

        val path = Path()
        path.moveTo(100F, 600F)
        path.cubicTo(250F, 500F, 400F, 700F, 550F, 600F)
        canvas.drawTextOnPath(text, path, 50F, 20F, paint)
        paint.strokeWidth = 1.toPixel(context)
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        canvas.drawPath(path, paint)

        return bitmapBuffer
    }

}
