package com.whatever.caramel.feature.login.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface LoginSideEffect : UiSideEffect {

    data object NavigateToStartDestination : LoginSideEffect

    data class ShowErrorSnackBar(val code: String, val message: String? = null) : LoginSideEffect

}