package me.zhang.workbench.text.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp
import me.zhang.workbench.x.drawMultilineText

/**
 * Created by Zhang on 2018/4/21 22:45.
 */
class TextView : View {

    private val text: CharSequence
    private val textPaint = TextPaint()

    init {
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.style = Paint.Style.FILL
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextView)

        text = a.getText(R.styleable.TextView_text)

        val textColor = a.getColor(R.styleable.TextView_textColor, Color.BLACK)
        textPaint.color = textColor
        val strokeWidth = a.getDimension(R.styleable.TextView_strokeWidth, 1.dp())
        textPaint.strokeWidth = strokeWidth

        val textSize = a.getDimension(R.styleable.TextView_textSize, 14.dp())
        textPaint.textSize = textSize

        a.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawMultilineText(
                text,
                textPaint,
                (measuredWidth - paddingLeft - paddingRight),
                x + paddingLeft,
                y + paddingTop
        )
    }

}