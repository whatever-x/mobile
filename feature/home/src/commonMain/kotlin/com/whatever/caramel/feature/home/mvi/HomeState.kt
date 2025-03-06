package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class HomeState(
    val text: String = ""
) : UiState