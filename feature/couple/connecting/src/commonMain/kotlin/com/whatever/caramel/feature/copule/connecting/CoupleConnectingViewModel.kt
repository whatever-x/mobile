package com.whatever.caramel.feature.copule.connecting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingIntent
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingSideEffect
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingState
import kotlinx.coroutines.delay

class CoupleConnectingViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CoupleConnectingState, CoupleConnectingSideEffect, CoupleConnectingIntent>(savedStateHandle) {
    init {
        launch {
            delay(2000L)
            postSideEffect(CoupleConnectingSideEffect.NavigateToMain)
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleConnectingState = CoupleConnectingState()

    override suspend fun handleIntent(intent: CoupleConnectingIntent) {
    }
}
