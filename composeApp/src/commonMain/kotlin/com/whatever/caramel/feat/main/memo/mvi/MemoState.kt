package com.whatever.caramel.feat.main.memo.mvi

import com.whatever.caramel.core.presentation.UiState

data class MemoState(
    val text: String = ""
) : UiState