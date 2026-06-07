package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class BalanceGameShareState(
    val isLoading: Boolean = false,
    val exportInProgress: Boolean = false,
) : UiState
