package com.whatever.caramel.feat.couple.invite.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface CoupleInviteSideEffect : UiSideEffect {

    data object NavigateToConnectCouple : CoupleInviteSideEffect

    data object NavigateToLogin : CoupleInviteSideEffect

}