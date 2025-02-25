package com.whatever.caramel.feat.couple.presentation.code

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.couple.presentation.code.mvi.CoupleCodeIntent
import com.whatever.caramel.feat.couple.presentation.code.mvi.CoupleCodeSideEffect
import com.whatever.caramel.feat.couple.presentation.code.mvi.CoupleCodeState

class CoupleCodeViewModel(
    savedStateHandle: SavedStateHandle

) : BaseViewModel<CoupleCodeState, CoupleCodeSideEffect, CoupleCodeIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleCodeState {
        return CoupleCodeState()
    }

    override suspend fun handleIntent(intent: CoupleCodeIntent) {
        when (intent) {
            is CoupleCodeIntent.ClickConnectButton -> postSideEffect(CoupleCodeSideEffect.NavigateToHome)
        }
    }

}