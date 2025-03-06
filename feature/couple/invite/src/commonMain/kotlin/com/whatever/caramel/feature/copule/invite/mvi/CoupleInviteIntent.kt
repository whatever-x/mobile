package com.whatever.caramel.feature.copule.invite.mvi

import com.whatever.caramel.core.viewmodel.UiIntent


sealed interface CoupleInviteIntent : UiIntent {

    data object ClickConnectCoupleButton : CoupleInviteIntent

    data object ClickCloseButton : CoupleInviteIntent

}