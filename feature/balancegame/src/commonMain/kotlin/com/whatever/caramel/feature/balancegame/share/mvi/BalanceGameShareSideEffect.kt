package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface BalanceGameShareSideEffect : UiSideEffect {
    data object NavigateToBack : BalanceGameShareSideEffect
}
