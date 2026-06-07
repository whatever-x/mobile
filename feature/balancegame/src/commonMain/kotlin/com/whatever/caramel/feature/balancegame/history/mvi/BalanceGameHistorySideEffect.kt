package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface BalanceGameHistorySideEffect : UiSideEffect {
    data object NavigateToBack : BalanceGameHistorySideEffect
}
