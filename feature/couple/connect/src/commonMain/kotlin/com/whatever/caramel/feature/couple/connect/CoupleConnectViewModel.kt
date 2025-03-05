package com.whatever.caramel.feature.couple.connect

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectIntent
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectSideEffect
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectState

class CoupleConnectViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CoupleConnectState, CoupleConnectSideEffect, CoupleConnectIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleConnectState {
        return CoupleConnectState()
    }

    override suspend fun handleIntent(intent: CoupleConnectIntent) {
        when (intent) {
            is CoupleConnectIntent.ClickConnectButton -> postSideEffect(CoupleConnectSideEffect.NavigateToMain)
            is CoupleConnectIntent.ClickBackButton -> postSideEffect(CoupleConnectSideEffect.NavigateToInviteCouple)
        }
    }

}