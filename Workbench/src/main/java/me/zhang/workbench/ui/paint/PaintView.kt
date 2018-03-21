package me.zhang.workbench.ui.paint

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintView : View {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}