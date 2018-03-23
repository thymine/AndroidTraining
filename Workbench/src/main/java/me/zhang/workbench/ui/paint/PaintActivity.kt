package me.zhang.workbench.ui.paint

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.activity_paint.*
import me.zhang.workbench.R

/**
 * Created by zhangxiangdong on 2018/3/20.
 */
class PaintActivity : AppCompatActivity() {

    companion object {
        const val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lineButton -> {
                    paintView.useThisPainter(LinePainter(paintView))
                }
                R.id.rectangleButton -> {
                    paintView.useThisPainter(RectanglePainter(paintView))
                }
                R.id.ovalButton -> {
                    paintView.useThisPainter(OvalPainter(paintView))
                }
            }
        }
        colorButton.setOnClickListener { _ ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(Color.RED)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setOnColorSelectedListener { selectedColor ->
                        Toast.makeText(this, "0x" + Integer.toHexString(selectedColor),
                                Toast.LENGTH_SHORT).show()
                    }
                    .setPositiveButton("Ok") { _, selectedColor, _ ->
                        changePaintColor(selectedColor)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .build()
                    .show()
        }
        clearButton.setOnClickListener { _ ->
            clearCanvas()
        }
        captureButton.setOnClickListener { _ ->
            // 检查读写权限
            if (isStoragePermissionGranted()) {
                captureCanvas()
            } else {
                // 请求读写权限
                requestWriteExternalStoragePermission()
            }
        }
    }

    private fun requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
    }

    private fun captureCanvas() {
        var captureFilePath = paintView.captureCanvas()
        if (TextUtils.isEmpty(captureFilePath)) {
            captureFilePath = "Capture failed, see logs"
        }
        Toast.makeText(this, captureFilePath, Toast.LENGTH_SHORT).show()
    }

    private fun clearCanvas() {
        paintView.clearCanvas()
    }

    private fun changePaintColor(selectedColor: Int) {
        paintView.changePaintColor(selectedColor)
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else { // permission is automatically granted on sdk < 23 upon installation
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureCanvas()
        }
    }

}