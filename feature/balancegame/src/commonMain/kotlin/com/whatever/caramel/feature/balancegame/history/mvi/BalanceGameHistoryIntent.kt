package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface BalanceGameHistoryIntent : UiIntent {
    data object ClickBackButton : BalanceGameHistoryIntent

    data class ClickHistoryCard(
        val gameId: Long,
    ) : BalanceGameHistoryIntent

    data class ClickShareResult(
        val gameId: Long,
    ) : BalanceGameHistoryIntent

    data object LoadMore : BalanceGameHistoryIntent
}
