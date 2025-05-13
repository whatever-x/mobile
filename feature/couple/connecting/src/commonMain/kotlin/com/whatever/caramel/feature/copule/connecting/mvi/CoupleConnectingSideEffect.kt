package com.whatever.caramel.feature.copule.connecting.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CoupleConnectingSideEffect : UiSideEffect {

    data object NavigateToMain : CoupleConnectingSideEffect

}