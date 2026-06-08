package com.whatever.caramel.feature.balancegame.history.mvi

import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState
import com.whatever.caramel.feature.balancegame.history.model.BalanceGameHistoryUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BalanceGameHistoryState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isEndReached: Boolean = false,
    val nextCursor: String? = null,
    val myNickname: String = "",
    val myGender: Gender = Gender.IDLE,
    val partnerNickname: String = "",
    val partnerGender: Gender = Gender.IDLE,
    val items: ImmutableList<BalanceGameHistoryUiModel> = persistentListOf(),
    val expandedGameId: Long? = null,
) : UiState
