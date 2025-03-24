package com.whatever.caramel.feature.splash

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.usecase.auth.RefreshAuthTokenUseCase
import com.whatever.caramel.core.domain.usecase.user.CheckUserStateUseCase
import com.whatever.caramel.core.domain.usecase.user.GetOnboardingCompletionUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashSideEffect
import com.whatever.caramel.feature.splash.mvi.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getUserStateUseCase: CheckUserStateUseCase,
    private val refreshAuthTokenUseCase: RefreshAuthTokenUseCase,
    private val getOnBoardingCompletionUseCase: GetOnboardingCompletionUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SplashState, SplashSideEffect, SplashIntent>(savedStateHandle) {

    private lateinit var userStatus: UserStatus

    init {
        launch {
            delay(1000L)
            checkUserState()
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

    private fun checkUserState() {
        launch {
            userStatus = getUserStateUseCase()
            when (userStatus) {
                UserStatus.NONE -> checkOnboardingCompletion()
                UserStatus.NEW, UserStatus.SINGLE, UserStatus.COUPLED -> refreshAuthToken()
            }
        }
    }

    private suspend fun refreshAuthToken() {
        refreshAuthTokenUseCase()
        when(userStatus){
            // @RyuSw-cs 2025.03.24 NONE이 나올수는 없지만 아무것도 하지 않는 상태가 되면 안되므로 로그인으로 이동
            UserStatus.NONE -> postSideEffect(SplashSideEffect.NavigateToLogin)
            UserStatus.NEW -> postSideEffect(SplashSideEffect.NavigateToCreateProfile)
            UserStatus.SINGLE -> postSideEffect(SplashSideEffect.NavigateToInviteCouple)
            UserStatus.COUPLED -> postSideEffect(SplashSideEffect.NavigateToMain)
        }
    }

    private suspend fun checkOnboardingCompletion() {
        if (getOnBoardingCompletionUseCase()) {
            postSideEffect(SplashSideEffect.NavigateToLogin)
        } else {
            postSideEffect(SplashSideEffect.NavigateToOnBoarding)
        }
    }
}