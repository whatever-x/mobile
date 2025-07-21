package com.whatever.caramel.feature.couple.connect.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CoupleConnectSideEffect : UiSideEffect {
    data object NavigateToMain : CoupleConnectSideEffect

    data object NavigateToInviteCouple : CoupleConnectSideEffect

    data class ShowSnackBarMessage(
        val message: String,
    ) : CoupleConnectSideEffect

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : CoupleConnectSideEffect

    data class ShowErrorToast(
        val message: String,
    ) : CoupleConnectSideEffect
}
