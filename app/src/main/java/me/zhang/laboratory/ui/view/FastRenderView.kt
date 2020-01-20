package me.zhang.laboratory.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import kotlin.random.Random

class FastRenderView : SurfaceView, Runnable {

    private var renderThread: Thread? = null
    @Volatile
    private var running: Boolean? = false
    private val rand = Random(System.currentTimeMillis())

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun resume() {
        running = true
        renderThread = Thread(this)
        renderThread?.start()
    }

    override fun run() {
        while (running == true) {
            if (holder.surface.isValid) {
                val canvas = holder.lockCanvas()
                canvas?.drawRGB(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256))
                holder.unlockCanvasAndPost(canvas)

                try {
                    Thread.sleep(500)
                } catch (e: Exception) {
                }
            }
        }
    }

    fun pause() {
        running = false
        while (true) {
            try {
                renderThread?.join()
                renderThread = null
                return
            } catch (e: Exception) {
            }
        }
    }

}