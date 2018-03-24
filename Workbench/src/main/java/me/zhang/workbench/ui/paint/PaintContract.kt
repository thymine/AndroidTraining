package me.zhang.workbench.ui.paint

import android.graphics.Paint

/**
 * Created by zhangxiangdong on 2018/3/23.
 */
interface PaintContract {

    interface PaintUi {

        fun onUndoButtonEnabled(enabled: Boolean)

        fun onRedoButtonEnabled(enabled: Boolean)

    }

    interface PaintPresenter {

        fun attachPaintUi(paintUi: PaintUi)

        fun changePainter(painter: ShapePainter)

        fun captureCanvas(): String?

        fun clearCanvas()

        fun changePaintColor(selectedColor: Int)

        fun undoPainting()

        /**
         * 设置用来显示临时的绘制过程的路径对象
         */
        fun setVisualTempShape(tempShape: Shape)

        fun addDrawingShape(shape: Shape)

        fun redoPainting()

        fun getPaint(): Paint

        fun redraw()

    }

}