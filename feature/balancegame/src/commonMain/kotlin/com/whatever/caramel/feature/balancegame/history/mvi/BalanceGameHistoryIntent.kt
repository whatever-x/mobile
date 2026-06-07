package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface BalanceGameHistoryIntent : UiIntent {
    data object ClickBackButton : BalanceGameHistoryIntent
}
