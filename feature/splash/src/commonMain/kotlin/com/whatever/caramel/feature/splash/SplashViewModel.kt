package com.whatever.caramel.feature.splash

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.deeplink.DeepLinkHandler
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.usecase.user.RefreshUserSessionUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashSideEffect
import com.whatever.caramel.feature.splash.mvi.SplashState
import kotlinx.coroutines.delay

class SplashViewModel(
    private val refreshUserSessionUseCase: RefreshUserSessionUseCase,
    private val deepLinkHandler: DeepLinkHandler,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<SplashState, SplashSideEffect, SplashIntent>(savedStateHandle, crashlytics) {
    init {
        launch {
            delay(1000L)
            deepLinkHandler.runningApp()
            val userStatus = refreshUserSessionUseCase()
            postSideEffect(SplashSideEffect.NavigateToStartDestination(userStatus = userStatus))
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable !is CaramelException) {
            caramelCrashlytics.recordException(throwable)
        }
        postSideEffect(SplashSideEffect.NavigateToLogin)
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): SplashState = SplashState()

    override suspend fun handleIntent(intent: SplashIntent) {}
}
