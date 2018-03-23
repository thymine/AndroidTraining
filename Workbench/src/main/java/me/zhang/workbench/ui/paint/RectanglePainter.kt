package me.zhang.workbench.ui.paint

import android.graphics.Path

/**
 * Created by zhangxiangdong on 2018/3/23.
 */
class RectanglePainter(paintView: PaintView) : BaseShapePainter(paintView) {

    override fun addShape(path: Path, left: Float, top: Float, right: Float, bottom: Float) {
        path.addRect(left, top, right, bottom, Path.Direction.CCW)
    }

}