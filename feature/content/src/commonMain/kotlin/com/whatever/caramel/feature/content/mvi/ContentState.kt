package com.whatever.caramel.feature.content.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class ContentState(
    val id : Long,
    val text: String = ""
) : UiState