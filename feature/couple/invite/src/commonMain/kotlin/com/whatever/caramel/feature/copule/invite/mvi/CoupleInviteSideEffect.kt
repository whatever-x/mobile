package com.whatever.caramel.feature.copule.invite.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CoupleInviteSideEffect : UiSideEffect {
    data object NavigateToConnectCouple : CoupleInviteSideEffect

    data object NavigateToLogin : CoupleInviteSideEffect

    data class CopyToClipBoardWithShowSnackBar(
        val inviteCode: String,
    ) : CoupleInviteSideEffect

    data class ShareOfInvite(
        val inviteCode: String,
    ) : CoupleInviteSideEffect

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : CoupleInviteSideEffect

    data class ShowErrorToast(
        val message: String,
    ) : CoupleInviteSideEffect
}
