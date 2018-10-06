package me.zhang.laboratory.utils

import android.content.Context
import me.zhang.laboratory.App

/**
 * Created by zhangxiangdong on 2018/2/5.
 */
fun Float.dp(): Float {
    return UiUtils.convertDpToPixel(this, App.getContext())
}

fun Float.dp(context: Context): Float {
    return UiUtils.convertDpToPixel(this, context)
}

fun Int.dp(): Float {
    return UiUtils.convertDpToPixel(toFloat(), App.getContext())
}

fun Int.dp(context: Context): Float {
    return UiUtils.convertDpToPixel(toFloat(), context)
}