package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface BalanceGameShareSideEffect : UiSideEffect {
    data object NavigateToBack : BalanceGameShareSideEffect

    sealed interface ShowSnackBar : BalanceGameShareSideEffect {
        data object ImageSaveSuccess : ShowSnackBar

        data object ImageSaveFailure : ShowSnackBar

        data object ImageShareFailure : ShowSnackBar

        data object GalleryPermissionRequired : ShowSnackBar
    }
}
