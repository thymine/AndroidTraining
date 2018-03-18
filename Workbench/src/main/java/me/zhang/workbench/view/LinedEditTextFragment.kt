package me.zhang.workbench.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp

/**
 * Created by Zhang on 3/18/2018 8:30 PM.
 */
class LinedEditTextFragment : Fragment() {

    companion object {
        fun newInstance(title: String): LinedEditTextFragment {
            val args = Bundle()
            args.putString(CustomViewActivity.FRAGMENT_TITLE, title)

            val fragment = LinedEditTextFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lined_edit_text, container, false)
    }

    class LinedEditText : EditText {

        private val paint = Paint()
        private val rect = Rect()

        constructor(context: Context?) : this(context, null)
        constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.dp()
            paint.color = Color.GRAY
        }

        override fun onDraw(canvas: Canvas) {
            val r = rect
            val p = paint
            val l = lineCount - 1
            for (i in 0..l) {
                val baseline = getLineBounds(i, r) * 1f
                canvas.drawLine(r.left * 1f, baseline, r.right * 1f, baseline, p)
            }
            super.onDraw(canvas)
        }

    }

}