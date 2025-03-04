package com.whatever.caramel.feat.splash.mvi

import com.whatever.caramel.core.presentation.UiState

data class SplashState(
    val text: String = ""
) : UiState