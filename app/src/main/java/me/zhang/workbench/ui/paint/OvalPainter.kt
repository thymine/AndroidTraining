package me.zhang.workbench.ui.paint

import android.graphics.Path
import android.graphics.RectF

/**
 * Created by zhangxiangdong on 2018/3/22.
 */
class OvalPainter(paintView: PaintView) : BaseShapePainter(paintView) {

    override fun addShape(path: Path, left: Float, top: Float, right: Float, bottom: Float) {
        path.addOval(RectF(left, top, right, bottom), Path.Direction.CCW)
    }

}