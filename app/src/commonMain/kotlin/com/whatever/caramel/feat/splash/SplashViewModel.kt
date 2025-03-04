package com.whatever.caramel.feat.splash

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.splash.mvi.SplashIntent
import com.whatever.caramel.feat.splash.mvi.SplashSideEffect
import com.whatever.caramel.feat.splash.mvi.SplashState
import kotlinx.coroutines.delay

class SplashViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SplashState, SplashSideEffect, SplashIntent>(savedStateHandle) {

    init {
        launch {
            delay(3000L)
            postSideEffect(SplashSideEffect.NavigateToOnBoarding)
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): SplashState {
        return SplashState()
    }

    override suspend fun handleIntent(intent: SplashIntent) {
        TODO()
    }

}