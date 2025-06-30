package com.whatever.caramel.feature.profile.create.mvi

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ProfileCreateSideEffect : UiSideEffect {
    data object NavigateToLogin : ProfileCreateSideEffect

    data class NavigateToStartDestination(val userStatus: UserStatus) : ProfileCreateSideEffect

    data object NavigateToServiceTermNotion : ProfileCreateSideEffect

    data object NavigateToPersonalInfoTermNotion : ProfileCreateSideEffect

    data object PerformHapticFeedback : ProfileCreateSideEffect

    data class ShowErrorToast(val message: String) : ProfileCreateSideEffect

    data class ShowErrorDialog(val message: String, val description: String?) : ProfileCreateSideEffect
}
