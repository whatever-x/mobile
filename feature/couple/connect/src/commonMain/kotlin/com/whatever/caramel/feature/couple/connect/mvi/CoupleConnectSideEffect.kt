package com.whatever.caramel.feature.couple.connect.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CoupleConnectSideEffect : UiSideEffect {

    data object NavigateToMain : CoupleConnectSideEffect

    data object NavigateToInviteCouple : CoupleConnectSideEffect

}