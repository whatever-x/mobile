package com.whatever.caramel.feature.balancegame.share.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.create
import platform.Photos.PHAccessLevelAddOnly
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosImageShareManager : ImageShareManager {
    override suspend fun saveToGallery(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit> =
        withContext(Dispatchers.Main) {
            runCatching {
                val uiImage = image.toUIImage()
                requestPhotoAddPermission()
                suspendCancellableCoroutine { continuation ->
                    PHPhotoLibrary.sharedPhotoLibrary().performChanges(
                        changeBlock = {
                            PHAssetChangeRequest.creationRequestForAssetFromImage(uiImage)
                        },
                        completionHandler = { success, error ->
                            if (continuation.isActive) {
                                if (success && error == null) {
                                    continuation.resume(Unit)
                                } else {
                                    continuation.resumeWithException(
                                        IllegalStateException(error?.localizedDescription ?: "Image save failed"),
                                    )
                                }
                            }
                        },
                    )
                }
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

    private suspend fun requestPhotoAddPermission() {
        val currentStatus = PHPhotoLibrary.authorizationStatusForAccessLevel(PHAccessLevelAddOnly)
        val status =
            if (currentStatus == PHAuthorizationStatusNotDetermined) {
                suspendCancellableCoroutine { continuation ->
                    PHPhotoLibrary.requestAuthorizationForAccessLevel(PHAccessLevelAddOnly) { newStatus ->
                        if (continuation.isActive) {
                            continuation.resume(newStatus)
                        }
                    }
                }
            } else {
                currentStatus
            }

        if (status != PHAuthorizationStatusAuthorized && status != PHAuthorizationStatusLimited) {
            throw PhotoLibraryPermissionDeniedException()
        }
    }
}
