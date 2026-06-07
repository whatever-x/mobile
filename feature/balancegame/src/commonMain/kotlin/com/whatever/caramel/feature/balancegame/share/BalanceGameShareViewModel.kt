package com.whatever.caramel.feature.balancegame.share

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.balancegame.share.image.ImageShareManager
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareIntent
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareSideEffect
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareState

class BalanceGameShareViewModel(
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
    private val imageShareManager: ImageShareManager,
) : BaseViewModel<BalanceGameShareState, BalanceGameShareSideEffect, BalanceGameShareIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameShareState = BalanceGameShareState()

    override suspend fun handleIntent(intent: BalanceGameShareIntent) {
        when (intent) {
            is BalanceGameShareIntent.ClickBackButton -> postSideEffect(BalanceGameShareSideEffect.NavigateToBack)
            is BalanceGameShareIntent.ClickSaveImage -> saveImage(intent.image)
            is BalanceGameShareIntent.ClickShareImage -> shareImage(intent.image)
        }
    }

    private fun saveImage(image: ImageBitmap) {
        if (currentState.exportInProgress) return
        launch {
            reduce { copy(exportInProgress = true) }
            val result = imageShareManager.saveToGallery(image = image, fileName = IMAGE_FILE_NAME)
            reduce { copy(exportInProgress = false) }
            val message = if (result.isSuccess) "이미지를 저장했어요." else "이미지 저장에 실패했어요."
            postSideEffect(BalanceGameShareSideEffect.ShowSnackBar(message))
        }
    }

    private fun shareImage(image: ImageBitmap) {
        if (currentState.exportInProgress) return
        launch {
            reduce { copy(exportInProgress = true) }
            val result = imageShareManager.shareImage(image = image, fileName = IMAGE_FILE_NAME)
            reduce { copy(exportInProgress = false) }
            if (result.isFailure) {
                postSideEffect(BalanceGameShareSideEffect.ShowSnackBar("이미지 공유에 실패했어요."))
            }
        }
    }

    companion object {
        private const val IMAGE_FILE_NAME = "caramel_balance_game"
    }
}
