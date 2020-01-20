package me.zhang.laboratory.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.ui.view.FastRenderView

class SurfaceViewTestActivity : AppCompatActivity() {
    private var fastRenderView: FastRenderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fastRenderView = FastRenderView(this)
        setContentView(fastRenderView)
    }

    override fun onResume() {
        super.onResume()
        fastRenderView?.resume()
    }

    override fun onPause() {
        super.onPause()
        fastRenderView?.pause()
    }

}
