package com.whatever.caramel.feat.couple.invite.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface CoupleInviteIntent : UiIntent {

    data object ClickConnectCoupleButton : CoupleInviteIntent

    data object ClickCloseButton : CoupleInviteIntent

}