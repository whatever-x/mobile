package com.whatever.caramel.feat.couple.code.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface CoupleConnectIntent : UiIntent {

    data object ClickConnectButton : CoupleConnectIntent

    data object ClickBackButton : CoupleConnectIntent

}