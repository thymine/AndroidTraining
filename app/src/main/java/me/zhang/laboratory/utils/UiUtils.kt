package me.zhang.laboratory.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import me.zhang.laboratory.App

/**
 * Created by zhangxiangdong on 2018/2/5.
 */
fun Float.dp(): Float {
    return convertDpToPixel(this, App.context)
}

fun Float.dp(context: Context): Float {
    return convertDpToPixel(this, context)
}

fun Int.dp(): Float {
    return convertDpToPixel(toFloat(), App.context)
}

fun Int.dp(context: Context): Float {
    return convertDpToPixel(toFloat(), context)
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px      A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun getScreenDimens(activity: Activity, out: IntArray?) {
    require(!(out == null || out.size != 2)) { "int[] out is not valid!" }
    val metrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(metrics)
    val height = metrics.heightPixels
    val width = metrics.widthPixels
    out[0] = width
    out[1] = height
}