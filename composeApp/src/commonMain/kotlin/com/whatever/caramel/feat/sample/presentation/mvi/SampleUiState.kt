package com.whatever.caramel.feat.sample.presentation.mvi

import com.whatever.caramel.core.presentation.UiState

data class SampleUiState(
    val text: String = ""
) : UiState
