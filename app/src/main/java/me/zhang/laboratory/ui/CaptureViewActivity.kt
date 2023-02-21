package me.zhang.laboratory.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import me.zhang.laboratory.databinding.ActivityCaptureViewBinding
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors


class CaptureViewActivity : BaseActivity() {

    companion object {
        const val WHAT_BITMAP_TO_FILE = 100
        const val WHAT_BITMAP_TO_FILE_FAILED = 101
        const val MY_PERMISSIONS_REQUEST = 200
    }

    private lateinit var binding: ActivityCaptureViewBinding

    private val callback: Handler.Callback = Handler.Callback {
        when (it.what) {
            WHAT_BITMAP_TO_FILE -> {
                Toast.makeText(this, "保存成功！", Toast.LENGTH_LONG).show()
                return@Callback true
            }
            WHAT_BITMAP_TO_FILE_FAILED -> {
                Toast.makeText(this, "保存失败！", Toast.LENGTH_LONG).show()
                return@Callback true
            }
        }
        false
    }
    private val handler = Handler(callback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaptureViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rp()
    }

    private fun rp() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST
                )
            }
        } else {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return
            }
        }
    }

    fun save(@Suppress("UNUSED_PARAMETER") v: View) {
        val bmp = getResizedBitmap(loadBitmapFromView(binding.llParent), 5f)
        Toast.makeText(this, "保存图片中…", Toast.LENGTH_LONG).show()
        Executors.newSingleThreadExecutor().submit {
            try {
                saveBitmapToLocal(bmp)
                handler.sendEmptyMessage(WHAT_BITMAP_TO_FILE)
            } catch (e: Exception) {
                handler.sendEmptyMessage(WHAT_BITMAP_TO_FILE)
            } finally {
            }
        }
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

    private fun getResizedBitmap(bmp: Bitmap, scale: Float): Bitmap {
        val width = bmp.width
        val height = bmp.height
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false)
    }

    @WorkerThread
    private fun saveBitmapToLocal(bmp: Bitmap) {
        val path = Environment.getExternalStorageDirectory().toString()
        val outFile = File(path, "title.jpg")
        val fos = FileOutputStream(outFile)

        bmp.compress(Bitmap.CompressFormat.WEBP, 100, fos)
        fos.flush()
        fos.close()

        MediaStore.Images.Media.insertImage(contentResolver, outFile.absolutePath, outFile.name, outFile.name)
    }

}
