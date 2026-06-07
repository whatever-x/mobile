package com.whatever.caramel.feature.balancegame.share

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareIntent
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareSideEffect
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareState

class BalanceGameShareViewModel(
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<BalanceGameShareState, BalanceGameShareSideEffect, BalanceGameShareIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameShareState = BalanceGameShareState()

    override suspend fun handleIntent(intent: BalanceGameShareIntent) {
        when (intent) {
            is BalanceGameShareIntent.ClickBackButton -> postSideEffect(BalanceGameShareSideEffect.NavigateToBack)
        }
    }
}
