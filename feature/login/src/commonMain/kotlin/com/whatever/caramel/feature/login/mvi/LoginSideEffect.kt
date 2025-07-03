package com.whatever.caramel.feature.login.mvi

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface LoginSideEffect : UiSideEffect {
    data class NavigateToStartDestination(
        val userStatus: UserStatus,
    ) : LoginSideEffect

    data class ShowErrorToast(
        val message: String,
    ) : LoginSideEffect

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : LoginSideEffect
}
