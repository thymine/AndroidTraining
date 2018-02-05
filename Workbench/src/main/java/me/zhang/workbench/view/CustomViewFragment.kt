package me.zhang.workbench.view

import android.graphics.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.zhang.workbench.R
import me.zhang.workbench.utils.UiUtils
import me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE

/**
 * Created by Zhang on 8/19/2017 6:52 PM.
 */
class CustomViewFragment : Fragment() {

    private var mGraphicsImage: ImageView? = null
    private var mCustomImage: ImageView? = null
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
    }

    private fun getDrawnBitmap(): Bitmap {
        val width = 480
        val height = 480
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)

        val radius = 24f
        val cx = width / 2f
        val cy = height / 2f

        /* draw circle */
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        canvas.drawCircle(cx, cy, radius, paint)

        /* draw rectangle */
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        val offset = 60f
        canvas.drawRect(cx - offset, cy - offset, cx + offset, cy + offset, paint)

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
        val bitmapBuffer = Bitmap.createBitmap(800, 600, Bitmap.Config.ARGB_4444)

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
        paint.strokeWidth = UiUtils.convertDpToPixel(3f, context)
        canvas.drawArc(rectF, 135f, 30f, false, paint)
        //endregion

        return bitmapBuffer
    }

}
