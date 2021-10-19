package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import me.zhang.laboratory.R

class CameraView : AppCompatImageView {

    private val camera: Camera = Camera()
    private val m: Matrix = Matrix()
    private val p: Paint = Paint()

    private var rotateX: Float = 0f
    private var rotateY: Float = 0f
    private var rotateZ: Float = 0f
    private var b: Bitmap? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        b = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.scenery), w, h, false)
    }

    override fun onDraw(canvas: Canvas?) {
        //region Draw background
        b?.let {
            p.alpha = 128
            canvas?.drawBitmap(it, 0f, 0f, p)
        }
        //endregion

        canvas?.save()

        camera.save()
        camera.rotate(rotateX, rotateY, rotateZ)
        val centerX = measuredWidth / 2.toFloat()
        val centerY = measuredHeight / 2.toFloat()
        camera.getMatrix(m)
        m.preTranslate(-centerX, -centerY)
        m.postTranslate(centerX, centerY)
        camera.restore()

        canvas?.setMatrix(m)

        super.onDraw(canvas)

        canvas?.restore()
    }

    fun setRotateX(rotateX: Float) {
        this.rotateX = rotateX
        postInvalidate()
    }

    fun setRotateY(rotateY: Float) {
        this.rotateY = rotateY
        postInvalidate()
    }

    fun setRotateZ(rotateZ: Float) {
        this.rotateZ = rotateZ
        postInvalidate()
    }

}