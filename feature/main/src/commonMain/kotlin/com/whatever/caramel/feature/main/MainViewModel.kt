package com.whatever.caramel.feature.main

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.main.mvi.MainIntent
import com.whatever.caramel.feature.main.mvi.MainSideEffect
import com.whatever.caramel.feature.main.mvi.MainState

class MainViewModel(
    private val fcmTokenProvider: FcmTokenProvider,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<MainState, MainSideEffect, MainIntent>(savedStateHandle, crashlytics) {
    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): MainState = MainState

    override suspend fun handleIntent(intent: MainIntent) {
    }

    fun updateFcmToken() {
        launch {
            fcmTokenProvider.updateToken()
        }
    }
}
