package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class BalanceGameHistoryState(
    val isLoading: Boolean = false,
) : UiState
