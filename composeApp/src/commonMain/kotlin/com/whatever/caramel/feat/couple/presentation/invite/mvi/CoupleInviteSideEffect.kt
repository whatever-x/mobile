package com.whatever.caramel.feat.couple.presentation.invite.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface CoupleInviteSideEffect : UiSideEffect {

    data object NavigateToCoupleCode : CoupleInviteSideEffect

}