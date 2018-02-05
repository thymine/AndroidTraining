package me.zhang.workbench.utils

import android.content.Context

/**
 * Created by zhangxiangdong on 2018/2/5.
 */
fun Float.toPixel(context: Context): Float {
    return UiUtils.convertDpToPixel(this, context)
}

fun Int.toPixel(context: Context): Float {
    return UiUtils.convertDpToPixel(toFloat(), context)
}