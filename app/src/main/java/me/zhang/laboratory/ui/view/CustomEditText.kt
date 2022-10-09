package me.zhang.laboratory.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import me.zhang.laboratory.R

@SuppressLint("AppCompatCustomView")
class CustomEditText : EditText {

    private lateinit var iconClear: Drawable

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)

        val clearResId =
            typedArray.getResourceId(R.styleable.CustomEditText_icon_clear, R.mipmap.ic_clear)
        iconClear = resources.getDrawable(clearResId, null)
        iconClear.setBounds(0, 0, 60, 60)

        typedArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setClearIconVisible(hasFocus() && text?.isNotEmpty() == true)
    }

    private fun setClearIconVisible(showClearIcon: Boolean) {
        setCompoundDrawables(null, null, if (showClearIcon) iconClear else null, null)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (aboveClearIcon(event)) {
                setText("")
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun aboveClearIcon(event: MotionEvent): Boolean {
        val x = event.x
        return (x > measuredWidth - iconClear.intrinsicWidth - paddingEnd) && (x < measuredWidth + paddingEnd)
    }

}