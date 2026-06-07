package com.whatever.caramel.feature.balancegame.share.mvi

import androidx.compose.ui.graphics.ImageBitmap
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface BalanceGameShareIntent : UiIntent {
    data object ClickBackButton : BalanceGameShareIntent

    data class ClickSaveImage(
        val image: ImageBitmap,
    ) : BalanceGameShareIntent

    data class ClickShareImage(
        val image: ImageBitmap,
    ) : BalanceGameShareIntent
}
