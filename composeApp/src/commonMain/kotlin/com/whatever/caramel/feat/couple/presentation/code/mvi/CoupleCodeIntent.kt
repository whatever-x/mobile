package com.whatever.caramel.feat.couple.presentation.code.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface CoupleCodeIntent : UiIntent {

    data object ClickConnectButton : CoupleCodeIntent

}