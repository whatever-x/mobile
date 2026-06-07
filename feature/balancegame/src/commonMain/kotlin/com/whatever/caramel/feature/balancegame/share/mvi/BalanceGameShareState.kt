package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class BalanceGameShareState(
    val isLoading: Boolean = false,
    val gameId: Long = 0L,
    val question: String = "",
    val myNickname: String = "",
    val myGender: String = "",
    val myChoice: String = "",
    val partnerNickname: String = "",
    val partnerGender: String = "",
    val partnerChoice: String = "",
) : UiState
