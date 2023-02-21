package me.zhang.laboratory.ui

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import me.zhang.laboratory.databinding.ActivityPickFileBinding
import me.zhang.laboratory.utils.FileUtils
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class PickFileActivity : BaseActivity() {

    companion object {
        const val LOG_TAG = "PickFileActivity"

        const val RC_PICK_FILE = 100
    }

    private lateinit var binding: ActivityPickFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPickFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickFile.setOnClickListener { pickFile() }
    }

    private fun pickFile() {
        val supportedMimeTypes = arrayOf(
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/pdf",
            "image/jpeg",
            "image/png"
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, supportedMimeTypes)
            intent.putExtra("android.content.extra.SHOW_ADVANCED", true)
            startActivityForResult(intent, RC_PICK_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == RC_PICK_FILE && resultCode == Activity.RESULT_OK) {
            parseFile(data.data)
        }
    }

    private fun parseFile(fileUri: Uri?) {
        if (fileUri == null) {
            toast("Invalid Uri")
            return
        }

        binding.tvFileUri.text = fileUri.toString()

        val expectedPath: String? = FileUtils.getRealPath(this, fileUri)
        if (TextUtils.isEmpty(expectedPath)) {
            // Write to specific path
            writeFile(fileUri)
        } else {
            showFilePath(expectedPath)
        }
    }

    private fun writeFile(fileUri: Uri) {
        WriteFileTask(this).execute(fileUri)
    }

    private fun showFilePath(filePath: String?) {
        binding.tvFilePath.text = if (TextUtils.isEmpty(filePath)) "null" else filePath
    }

    private fun toast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingProgress() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun dismissLoadingProgress() {
        binding.pbLoading.visibility = View.GONE
    }

    class WriteFileTask(host: PickFileActivity) : AsyncTask<Uri, Void, String?>() {

        private val hostRef = WeakReference<PickFileActivity>(host)

        override fun onPreExecute() {
            super.onPreExecute()
            hostRef.get()?.showLoadingProgress()
        }

        override fun doInBackground(vararg params: Uri): String? {
            val context = hostRef.get()?.applicationContext ?: return null

            val fileUri = params[0]
            val resolver = context.contentResolver ?: return null

            val cacheDir = context.externalCacheDir ?: return null

            var cursor: Cursor? = null
            try {
                cursor = resolver.query(fileUri, null, null, null, null)
                if (cursor == null) return null

                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                val filename = cursor.getString(nameIndex)

                val outFile = File(cacheDir.absolutePath + "/" + filename)
                val bis = BufferedInputStream(resolver.openInputStream(fileUri))
                Log.d(LOG_TAG, "available: " + bis.available())

                val fos = FileOutputStream(outFile)

                val data = ByteArray(8096)
                var total = 0L
                var count: Int
                while (bis.read(data).also { count = it } != -1) {
                    total += count
                    fos.write(data, 0, count)
                }
                Log.d(LOG_TAG, "total: $total")

                fos.flush()
                fos.close()

                return outFile.absolutePath
            } catch (e: Exception) {
                e.message?.let { Log.e(LOG_TAG, it) }
            } finally {
                cursor?.close()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            hostRef.get()?.dismissLoadingProgress()
            hostRef.get()?.showFilePath(result)
        }

    }

}
