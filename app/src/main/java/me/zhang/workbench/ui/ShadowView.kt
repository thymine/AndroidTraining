package me.zhang.workbench.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import me.zhang.workbench.utils.dp

/**
 * Created by zhangxiangdong on 2018/3/24.
 */
class ShadowView : View {

    companion object {
        const val TEXT = "Shadow视图"
    }

    private val paint = Paint()
    private val dashLinePath = Path()

    init {
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.BLACK
        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24f, resources.displayMetrics)
        paint.strokeWidth = 1.dp()

        paint.pathEffect = DashPathEffect(floatArrayOf(5f, 10f, 15f, 20f), 0f)
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // https://stackoverflow.com/a/17414651/3094830
        setLayerType(View.LAYER_TYPE_SOFTWARE, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val halfWidth = measuredWidth / 2f
        val halfHeight = measuredHeight / 2f

        val textWidth = paint.measureText(TEXT)
        val textX = halfWidth - textWidth / 2f
        val textY = halfHeight - (paint.descent() + paint.ascent()) / 2

        // 绘制文字使用填充样式
        paint.style = Paint.Style.FILL
        paint.setShadowLayer(10f, 2f, 2f, Color.RED)
        canvas.drawText(TEXT, textX, textY, paint)

        // 使用Stroke样式绘制虚线
        paint.style = Paint.Style.STROKE
        // 绘制水平中线
        paint.setShadowLayer(6f, 0f, 0f, Color.BLUE)
        dashLinePath.reset()
        dashLinePath.moveTo(0f, halfHeight)
        dashLinePath.quadTo(halfWidth, halfHeight, measuredWidth * 1f, halfHeight)
        canvas.drawPath(dashLinePath, paint)

        // 绘制垂直中线
        paint.setShadowLayer(0f, 0f, 0f, 0) // 移除阴影效果
        dashLinePath.reset()
        dashLinePath.moveTo(halfWidth, 0f)
        dashLinePath.quadTo(halfWidth, halfHeight, halfWidth, measuredHeight * 1f)
        canvas.drawPath(dashLinePath, paint)
    }

}