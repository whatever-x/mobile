package com.whatever.caramel.feature.splash.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface SplashSideEffect : UiSideEffect {

    data object NavigateToLogin : SplashSideEffect

    data object NavigateToMain : SplashSideEffect

    data object NavigateToCreateProfile : SplashSideEffect

    data object NavigateToInviteCouple : SplashSideEffect

}