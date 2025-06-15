package com.whatever.caramel.feature.main

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.main.mvi.MainIntent
import com.whatever.caramel.feature.main.mvi.MainSideEffect
import com.whatever.caramel.feature.main.mvi.MainState

class MainViewModel(
    private val fcmTokenProvider: FcmTokenProvider,
    savedStateHandle: SavedStateHandle,
): BaseViewModel<MainState, MainSideEffect, MainIntent>(savedStateHandle) {

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)

        TODO()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): MainState {
        return MainState
    }

    override suspend fun handleIntent(intent: MainIntent) {
        TODO()
    }

    fun updateFcmToken() {
        launch {
            fcmTokenProvider.updateToken()
        }
    }

}