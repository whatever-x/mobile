package com.whatever.caramel.feat.login.presentation.mvi

import com.whatever.caramel.core.presentation.UiState

data class LoginState(
    val text: String = "",
) : UiState