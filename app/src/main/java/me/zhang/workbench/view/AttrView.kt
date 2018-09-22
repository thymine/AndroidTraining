package me.zhang.workbench.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import me.zhang.workbench.R

class AttrView : View {

    companion object {
        const val LOG_TAG = "AttrView"
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.AttrView)
        val attr1 = ta?.getString(R.styleable.AttrView_attr1)
        val attr2 = ta?.getString(R.styleable.AttrView_attr2)
        val attr3 = ta?.getString(R.styleable.AttrView_attr3)
        val attr4 = ta?.getString(R.styleable.AttrView_attr4)

        Log.d(LOG_TAG, "attr1: $attr1")
        Log.d(LOG_TAG, "attr2: $attr2")
        Log.d(LOG_TAG, "attr3: $attr3")
        Log.d(LOG_TAG, "attr4: $attr4")
        ta?.recycle()
    }

}