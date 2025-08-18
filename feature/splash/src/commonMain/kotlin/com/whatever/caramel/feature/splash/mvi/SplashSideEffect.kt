package com.whatever.caramel.feature.splash.mvi

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface SplashSideEffect : UiSideEffect {
    data class NavigateToStartDestination(
        val userStatus: UserStatus,
    ) : SplashSideEffect

    data object NavigateToLogin : SplashSideEffect

    data object GoToStore : SplashSideEffect
}
