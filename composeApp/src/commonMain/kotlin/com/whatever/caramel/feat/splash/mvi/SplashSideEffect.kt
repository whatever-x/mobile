package com.whatever.caramel.feat.splash.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface SplashSideEffect : UiSideEffect {

    data object NavigateToOnBoarding : SplashSideEffect

    data object NavigateToLogin : SplashSideEffect

    data object NavigateToMain : SplashSideEffect

}