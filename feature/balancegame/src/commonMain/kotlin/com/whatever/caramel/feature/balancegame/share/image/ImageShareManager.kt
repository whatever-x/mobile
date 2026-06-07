package com.whatever.caramel.feature.balancegame.share.image

import androidx.compose.ui.graphics.ImageBitmap

interface ImageShareManager {
    suspend fun saveToGallery(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit>

    suspend fun shareImage(
        image: ImageBitmap,
        fileName: String,
    ): Result<Unit>
}
