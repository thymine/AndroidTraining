package me.zhang.workbench.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import me.zhang.workbench.R
import me.zhang.workbench.view.CustomViewActivity.FRAGMENT_TITLE

/**
 * Created by Zhang on 8/19/2017 6:52 PM.
 */
class CustomViewFragment : Fragment() {

    private var mCustomImage: ImageView? = null

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
    }

    private fun getDrawnBitmap(): Bitmap {
        val width = 480
        val height = 480
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)

        val radius = 24
        val cx = width / 2
        val cy = height / 2

        /* draw circle */
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), paint)

        /* draw rectangle */
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        val offset = 60
        canvas.drawRect((cx - offset).toFloat(), (cy - offset).toFloat(), (cx + offset).toFloat(), (cy + offset).toFloat(), paint)

        return bitmap
    }

}
