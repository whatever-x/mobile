package com.whatever.caramel.feature.balancegame.history

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryIntent
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistorySideEffect
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryState

class BalanceGameHistoryViewModel(
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<BalanceGameHistoryState, BalanceGameHistorySideEffect, BalanceGameHistoryIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameHistoryState = BalanceGameHistoryState()

    override suspend fun handleIntent(intent: BalanceGameHistoryIntent) {
        when (intent) {
            is BalanceGameHistoryIntent.ClickBackButton -> postSideEffect(BalanceGameHistorySideEffect.NavigateToBack)
        }
    }
}
