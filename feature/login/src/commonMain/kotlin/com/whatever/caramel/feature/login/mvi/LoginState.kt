package com.whatever.caramel.feature.login.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class LoginState(
    val text: String = "",
) : UiState

enum class SocialAuthType {
    KAKAO,
    APPLE,
    ;
}