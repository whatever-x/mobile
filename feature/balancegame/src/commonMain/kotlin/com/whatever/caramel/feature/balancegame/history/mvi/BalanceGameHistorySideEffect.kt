package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface BalanceGameHistorySideEffect : UiSideEffect {
    data object NavigateToBack : BalanceGameHistorySideEffect

    data class NavigateToShare(
        val gameId: Long,
        val question: String,
        val myNickname: String,
        val myGender: String,
        val myChoice: String,
        val partnerNickname: String,
        val partnerGender: String,
        val partnerChoice: String,
    ) : BalanceGameHistorySideEffect
}
