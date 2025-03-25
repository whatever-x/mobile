package com.whatever.caramel.feature.copule.invite.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CoupleInviteSideEffect : UiSideEffect {

    data object NavigateToConnectCouple : CoupleInviteSideEffect

    data object NavigateToLogin : CoupleInviteSideEffect

    data class CopyToClipBoardWithShowSnackBar(val inviteCode: String) : CoupleInviteSideEffect

}