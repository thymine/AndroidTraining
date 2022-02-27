package me.zhang.laboratory.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import me.zhang.laboratory.R
import java.util.*

class CameraView : AppCompatImageView {

    companion object {
        const val TAG = "CameraView"
    }

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

        //region CAMERA
        camera.save()
        // onDraw: camera(0.000000, 0.000000, -8.000000)
        // -8 * 72 = -578px
        Log.d(
            TAG,
            String.format(
                Locale.getDefault(),
                "onDraw: camera(%f, %f, %f)",
                camera.locationX,
                camera.locationY,
                camera.locationZ
            )
        )
        camera.rotate(rotateX, rotateY, rotateZ)

        toImageCenterC()

//        toImageCenterM()

        camera.restore()
        //endregion

        canvas?.setMatrix(m)

        super.onDraw(canvas)

        canvas?.restore()
    }

    private fun toImageCenterC() {
        val centerX = measuredWidth / 2.toFloat()
        val centerY = measuredHeight / 2.toFloat()

        val centerCX = centerX / 72
        val centerCY = centerY / 72

        camera.setLocation(centerCX, -centerCY, camera.locationZ)

        camera.getMatrix(m)
    }

    private fun toImageCenterM() {
        val centerX = measuredWidth / 2.toFloat()
        val centerY = measuredHeight / 2.toFloat()

        camera.getMatrix(m)

        m.preTranslate(-centerX, -centerY)
        m.postTranslate(centerX, centerY)
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