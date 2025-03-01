package com.whatever.caramel.feat.main.home.mvi

import com.whatever.caramel.core.presentation.UiState

data class HomeState(
    val text: String = ""
) : UiState