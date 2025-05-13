package com.whatever.caramel.feature.splash.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface SplashSideEffect : UiSideEffect {

    data object NavigateToStartDestination : SplashSideEffect

    data object NavigateToLogin : SplashSideEffect

}