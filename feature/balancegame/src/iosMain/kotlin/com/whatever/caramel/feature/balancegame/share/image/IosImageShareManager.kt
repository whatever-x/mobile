package com.whatever.caramel.feature.balancegame.share.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageWriteToSavedPhotosAlbum

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosImageShareManager : ImageShareManager {
    override suspend fun saveToGallery(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit> =
        withContext(Dispatchers.Main) {
            runCatching {
                UIImageWriteToSavedPhotosAlbum(image.toUIImage(), null, null, null)
            }
        }

    override suspend fun shareImage(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit> =
        withContext(Dispatchers.Main) {
            runCatching {
                val controller =
                    UIActivityViewController(
                        activityItems = listOf(image.toUIImage()),
                        applicationActivities = null,
                    )
                val rootViewController =
                    UIApplication.sharedApplication.keyWindow?.rootViewController
                        ?: error("rootViewController is null")
                rootViewController.presentViewController(controller, animated = true, completion = null)
            }
        }

    private fun ImageBitmap.toUIImage(): UIImage {
        val skiaImage = Image.makeFromBitmap(asSkiaBitmap())
        val pngData =
            skiaImage.encodeToData(EncodedImageFormat.PNG)
                ?: error("PNG encoding failed")
        val bytes = pngData.bytes
        val nsData =
            bytes.usePinned { pinned ->
                NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
            }
        return UIImage.imageWithData(nsData) ?: error("UIImage creation failed")
    }
}
