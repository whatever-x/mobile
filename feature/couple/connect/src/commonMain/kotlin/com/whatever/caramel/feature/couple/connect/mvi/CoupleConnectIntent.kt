package com.whatever.caramel.feature.couple.connect.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface CoupleConnectIntent : UiIntent {

    data object ClickConnectButton : CoupleConnectIntent

    data object ClickBackButton : CoupleConnectIntent

}