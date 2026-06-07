package com.whatever.caramel.feature.balancegame.share

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareIntent
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareSideEffect
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareState
import com.whatever.caramel.feature.balancegame.share.navigation.BalanceGameShareRoute

class BalanceGameShareViewModel(
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<BalanceGameShareState, BalanceGameShareSideEffect, BalanceGameShareIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameShareState {
        val args = savedStateHandle.toRoute<BalanceGameShareRoute>()
        return BalanceGameShareState(
            gameId = args.gameId,
            question = args.question,
            myNickname = args.myNickname,
            myGender = args.myGender,
            myChoice = args.myChoice,
            partnerNickname = args.partnerNickname,
            partnerGender = args.partnerGender,
            partnerChoice = args.partnerChoice,
        )
    }

    override suspend fun handleIntent(intent: BalanceGameShareIntent) {
        when (intent) {
            is BalanceGameShareIntent.ClickBackButton -> postSideEffect(BalanceGameShareSideEffect.NavigateToBack)
        }
    }
}
