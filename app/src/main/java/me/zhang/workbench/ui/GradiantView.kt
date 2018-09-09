package me.zhang.workbench.ui

import android.content.Context
import android.graphics.*
import android.graphics.Shader.TileMode.*
import android.util.AttributeSet
import android.view.View
import me.zhang.workbench.R
import me.zhang.workbench.utils.dp

/**
 * Created by zhangxiangdong on 2018/3/24.
 */
class GradiantView : View {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var linearGradient: LinearGradient? = null
    private var radialGradient: RadialGradient? = null
    private var sweepGradient: SweepGradient? = null
    private var sweepGradientStroke: SweepGradient? = null
    private var linearGradientMulti: LinearGradient? = null
    private var bitmapShader: BitmapShader? = null
    private var composeShader: ComposeShader? = null
    private val rectF = RectF()

    private val rotateMatrix = Matrix()
    private var rotateGradient: SweepGradient? = null
    private var rotations: Float = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(size * 4, mode))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfWidth = measuredWidth / 2f
        val qtrWidth = measuredWidth * 1f / 4
        val width = measuredWidth * 1f
        if (linearGradient == null) {
            linearGradient = LinearGradient(0f, 0f, halfWidth, halfWidth, Color.RED, Color.GREEN, CLAMP)
        }
        if (radialGradient == null) {
            radialGradient = RadialGradient(qtrWidth * 3, qtrWidth, qtrWidth, Color.BLUE, Color.RED, CLAMP)
        }
        if (sweepGradient == null) {
            sweepGradient = SweepGradient(qtrWidth, qtrWidth, Color.CYAN, Color.YELLOW)
        }
        if (sweepGradientStroke == null) {
            sweepGradientStroke = SweepGradient(qtrWidth * 3, qtrWidth, Color.MAGENTA, Color.CYAN)
        }
        if (linearGradientMulti == null) {
            linearGradientMulti = LinearGradient(0f, 0f, width, halfWidth, intArrayOf(Color.RED, Color.GREEN, Color.BLUE), floatArrayOf(0f, 0.3f, 1f), CLAMP)
        }
        if (bitmapShader == null) {
            bitmapShader = BitmapShader(BitmapFactory.decodeResource(resources, R.drawable.droid_64px), REPEAT, MIRROR)
        }
        if (composeShader == null) {
            composeShader = ComposeShader(
                    LinearGradient(0f, 0f, halfWidth, halfWidth, Color.RED, Color.GREEN, CLAMP),
                    bitmapShader, PorterDuff.Mode.DST_ATOP
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = measuredWidth * 1f
        val halfWidth = measuredWidth / 2f
        val qtrWidth = measuredWidth / 4f

        paint.shader = linearGradient
        paint.style = Paint.Style.FILL
        rectF.set(0f, 0f, halfWidth, halfWidth)
        canvas.drawRect(rectF, paint)

        paint.shader = radialGradient
        canvas.drawCircle(qtrWidth * 3, qtrWidth, qtrWidth, paint)

        canvas.translate(0f, halfWidth) // 向下平移画布!!!

        paint.shader = sweepGradient
        canvas.drawCircle(qtrWidth, qtrWidth, qtrWidth, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3.dp()
        paint.shader = sweepGradientStroke
        rectF.set(halfWidth, 0f, width, halfWidth)
        rectF.inset(qtrWidth / 4f, qtrWidth / 4f)
        canvas.drawRoundRect(rectF, 24.dp(), 24.dp(), paint)

        canvas.translate(0f, halfWidth)
        paint.style = Paint.Style.FILL
        paint.shader = linearGradientMulti
        rectF.set(0f, 0f, width, halfWidth)
        rectF.inset(qtrWidth / 4f, qtrWidth / 4f)
        canvas.drawOval(rectF, paint)

        canvas.translate(0f, halfWidth)
        rectF.set(0f, 0f, width, halfWidth)
        paint.shader = bitmapShader
        canvas.drawRect(rectF, paint)

        canvas.translate(0f, halfWidth)
        paint.shader = composeShader
        canvas.drawRect(rectF, paint)

        canvas.translate(0f, halfWidth)
        if (rotateGradient == null) {
            rotateGradient = SweepGradient(halfWidth, halfWidth, intArrayOf(Color.RED, Color.YELLOW, Color.GREEN), floatArrayOf(0f, 0.5f, 1f))
        }
        paint.shader = rotateGradient
        rotateGradient!!.setLocalMatrix(rotateMatrix)
        rotateMatrix.setRotate(rotations, halfWidth, halfWidth)
        rotations += 3f
        if (rotations >= 360f) {
            rotations = 0f
        }
        invalidate()
        canvas.drawCircle(halfWidth, halfWidth, halfWidth * 0.9f, paint)
    }

}