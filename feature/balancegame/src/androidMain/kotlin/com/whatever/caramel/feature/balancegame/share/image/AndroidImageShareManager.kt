package com.whatever.caramel.feature.balancegame.share.image

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AndroidImageShareManager(
    private val context: Context,
) : ImageShareManager {
    override suspend fun saveToGallery(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val bitmap = image.toSoftwareBitmap()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveViaMediaStore(bitmap, fileName)
                } else {
                    saveLegacy(bitmap, fileName)
                }
            }
        }

    override suspend fun shareImage(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val file = writePngToCache(image.toSoftwareBitmap(), fileName)
                val uri =
                    FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file,
                    )
                val shareIntent =
                    Intent(Intent.ACTION_SEND).apply {
                        type = MIME_PNG
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                context.startActivity(
                    Intent.createChooser(shareIntent, "공유하기").apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    },
                )
            }
        }

    private fun saveViaMediaStore(
        bitmap: Bitmap,
        fileName: String,
    ) {
        val resolver = context.contentResolver
        val values =
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.png")
                put(MediaStore.Images.Media.MIME_TYPE, MIME_PNG)
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/Caramel")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        val uri =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                ?: error("MediaStore insert returned null")
        resolver.openOutputStream(uri)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } ?: error("openOutputStream returned null")
        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, values, null, null)
    }

    private fun saveLegacy(
        bitmap: Bitmap,
        fileName: String,
    ) {
        @Suppress("DEPRECATION")
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dir = File(picturesDir, "Caramel").apply { if (!exists()) mkdirs() }
        val file = File(dir, "$fileName.png")
        FileOutputStream(file).use { out -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) }
        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), arrayOf(MIME_PNG), null)
    }

    private fun writePngToCache(
        bitmap: Bitmap,
        fileName: String,
    ): File {
        val dir = File(context.cacheDir, "shared_images").apply { if (!exists()) mkdirs() }
        val file = File(dir, "$fileName.png")
        FileOutputStream(file).use { out -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) }
        return file
    }

    private fun ImageBitmap.toSoftwareBitmap(): Bitmap {
        val androidBitmap = asAndroidBitmap()
        return if (androidBitmap.config == Bitmap.Config.HARDWARE) {
            androidBitmap.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            androidBitmap
        }
    }

    companion object {
        private const val MIME_PNG = "image/png"
    }
}
