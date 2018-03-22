package me.zhang.workbench.ui.paint

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
class OvalPainter(paintView: PaintView) : RectanglePainter(paintView) {

    override fun drawShape(canvas: Canvas, rectF: RectF, paint: Paint) {
        canvas.drawOval(rectF, paint)
    }

}