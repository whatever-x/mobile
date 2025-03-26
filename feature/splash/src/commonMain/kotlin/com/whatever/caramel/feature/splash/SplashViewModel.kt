package com.whatever.caramel.feature.splash

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.usecase.user.RefreshUserSessionUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashSideEffect
import com.whatever.caramel.feature.splash.mvi.SplashState
import kotlinx.coroutines.delay

class SplashViewModel(
    private val refreshUserSessionUseCase: RefreshUserSessionUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SplashState, SplashSideEffect, SplashIntent>(savedStateHandle) {

    init {
        launch {
            delay(1000L)
            checkAppAuthToken()
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        postSideEffect(SplashSideEffect.NavigateToLogin)
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): SplashState {
        return SplashState()
    }

    override suspend fun handleIntent(intent: SplashIntent) {}

    private suspend fun checkAppAuthToken() {
        val userStatus: UserStatus = refreshUserSessionUseCase()
        when (userStatus) {
            // @RyuSw-cs 2025.03.24 NONE인 경우 로그인을 하지 않은 초기 사용자
            UserStatus.NONE -> postSideEffect(SplashSideEffect.NavigateToLogin)
            UserStatus.NEW -> postSideEffect(SplashSideEffect.NavigateToCreateProfile)
            UserStatus.SINGLE -> postSideEffect(SplashSideEffect.NavigateToInviteCouple)
            UserStatus.COUPLED -> postSideEffect(SplashSideEffect.NavigateToMain)
        }
    }
}