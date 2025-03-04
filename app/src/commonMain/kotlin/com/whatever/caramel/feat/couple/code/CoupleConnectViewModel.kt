package com.whatever.caramel.feat.couple.code

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.couple.code.mvi.CoupleConnectIntent
import com.whatever.caramel.feat.couple.code.mvi.CoupleConnectSideEffect
import com.whatever.caramel.feat.couple.code.mvi.CoupleConnectState

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