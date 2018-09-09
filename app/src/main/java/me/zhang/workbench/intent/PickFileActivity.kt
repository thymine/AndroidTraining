package me.zhang.workbench.intent

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pick_file.*
import me.zhang.workbench.R
import java.io.IOException
import java.util.concurrent.TimeUnit

open class PickFileActivity : AppCompatActivity() {

    companion object {
        const val GALLERY_INTENT_CALLED: Int = 100
        const val GALLERY_KITKAT_INTENT_CALLED: Int = 200
        const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: Int = 300
        const val LOG_TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_file)
    }

    open fun onPickFile(view: View?) {
        if (checkPermission(this)) {
            startPickFile()
        }
    }

    private fun startPickFile() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            val intent = Intent()
            intent.type = "image/* video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, GALLERY_INTENT_CALLED)
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
            startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (null == data) return
        val originalUri = data.data

        if (originalUri.toString().contains("image")) {
            parseImage(requestCode, originalUri, data)
        } else if (originalUri.toString().contains("video")) {
            parseVideo(requestCode, originalUri, data)
        }
    }

    private fun parseImage(requestCode: Int, imageUri: Uri, data: Intent) {
        var imageBitmap: Bitmap? = null
        if (requestCode == GALLERY_INTENT_CALLED) {
            val realPathFromURI = getFileRealPathForLengcyUri(imageUri, MediaStore.Images.Media.DATA)
            imageBitmap = BitmapFactory.decodeFile(realPathFromURI)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && requestCode == GALLERY_KITKAT_INTENT_CALLED) {
            val takeFlags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(imageUri, takeFlags)
            imageBitmap = getBitmapFromUri(imageUri)
        }

        fileUriText.text = imageUri.toString()
        pickedImage.setImageBitmap(imageBitmap)
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    @Suppress("ConvertTryFinallyToUseCall")
    private fun getFileRealPathForLengcyUri(contentUri: Uri, column: String): String? {
        var realPath: String? = null
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor != null) {
            try {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                if (cursor.moveToFirst()) {
                    realPath = cursor.getString(columnIndex)
                }
            } finally {
                cursor.close()
            }
        }
        return realPath
    }

    private fun parseVideo(requestCode: Int, videoUri: Uri, data: Intent) {
        var realVideoPath: String? = null
        if (requestCode == GALLERY_INTENT_CALLED) {
            realVideoPath = getFileRealPathForLengcyUri(videoUri, MediaStore.Video.Media.DATA)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && requestCode == GALLERY_KITKAT_INTENT_CALLED) {
            val takeFlags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(videoUri, takeFlags)

            val wholeID = DocumentsContract.getDocumentId(videoUri) // "video:123"
            val id = wholeID.split(":")[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"
            val cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, column, sel, arrayOf(id), null)
            cursor?.use { c ->
                val columnIndex = c.getColumnIndex(column[0])
                if (c.moveToFirst()) {
                    realVideoPath = c.getString(columnIndex)
                    Log.d(LOG_TAG, realVideoPath)
                }
            }

        }
        fileUriText.text = videoUri.toString()

        //region Duration
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this, videoUri)
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.release()
        var videoLength: String? = null
        if (duration != null) {
            val millis = duration.toLong()
            videoLength = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
        }
        videoLengthText.text = videoLength
        //endregion

        //region Thumbnail
        val coverBitmap = ThumbnailUtils.createVideoThumbnail(realVideoPath, MediaStore.Video.Thumbnails.MINI_KIND)
        pickedVideo.setImageBitmap(coverBitmap)
        //endregion
    }

    private fun checkPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                                context as Activity,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    ActivityCompat
                            .requestPermissions(
                                    context,
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    private fun showDialog(msg: String, context: Context, permission: String) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle("Permission necessary")
        alertBuilder.setMessage("$msg permission is necessary")
        alertBuilder.setPositiveButton(android.R.string.yes,
                { _, _ ->
                    ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(permission),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                })
        val alert = alertBuilder.create()
        alert.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startPickFile()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
        }
    }

}
