package com.whatever.caramel.feat.couple.code.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface CoupleConnectSideEffect : UiSideEffect {

    data object NavigateToMain : CoupleConnectSideEffect

    data object NavigateToInviteCouple : CoupleConnectSideEffect

}