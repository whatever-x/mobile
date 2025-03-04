package com.whatever.caramel.feat.login.mvi

import com.whatever.caramel.core.presentation.UiState

data class LoginState(
    val text: String = "",
) : UiState

enum class SocialAuthType {
    KAKAO,
    APPLE,
    ;
}