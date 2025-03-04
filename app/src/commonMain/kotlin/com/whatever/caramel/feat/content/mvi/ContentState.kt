package com.whatever.caramel.feat.content.mvi

import com.whatever.caramel.core.presentation.UiState

data class ContentState(
    val text: String = ""
) : UiState