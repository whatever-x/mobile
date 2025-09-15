package com.whatever.caramel.feature.splash.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class SplashState(
    val text: String = "",
    val isForceUpdate: Boolean = false,
    val storeUri: String = "",
) : UiState
