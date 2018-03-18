package me.zhang.workbench.utils

import me.zhang.workbench.App

/**
 * Created by zhangxiangdong on 2018/2/5.
 */
fun Float.dp(): Float {
    return UiUtils.convertDpToPixel(this, App.getContext())
}

fun Int.dp(): Float {
    return UiUtils.convertDpToPixel(toFloat(), App.getContext())
}