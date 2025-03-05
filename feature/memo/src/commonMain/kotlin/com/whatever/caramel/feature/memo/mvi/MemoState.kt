package com.whatever.caramel.feature.memo.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class MemoState(
    val text: String = ""
) : UiState