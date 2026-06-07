package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState

data class BalanceGameShareState(
    val isLoading: Boolean = false,
    val exportInProgress: Boolean = false,
    val question: String = "",
    val myChoice: String = "",
    val partnerChoice: String = "",
    val myGender: Gender = Gender.IDLE,
    val partnerGender: Gender = Gender.IDLE,
    val isSameChoice: Boolean = false,
) : UiState
