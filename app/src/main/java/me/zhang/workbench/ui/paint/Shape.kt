package me.zhang.workbench.ui.paint

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

/**
 * Created by zhangxiangdong on 2018/3/24.
 */
data class Shape(val path: Path, val color: Int) {

    fun draw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawPath(path, paint)
    }

}