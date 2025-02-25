package com.whatever.caramel.feat.couple.presentation.invite.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface CoupleInviteIntent : UiIntent {

    data object ClickInputCodeButton : CoupleInviteIntent

}