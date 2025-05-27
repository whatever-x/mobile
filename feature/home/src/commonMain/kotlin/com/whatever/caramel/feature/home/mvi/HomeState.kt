package com.whatever.caramel.feature.home.mvi

import androidx.compose.runtime.Immutable
import com.whatever.caramel.core.viewmodel.UiState

data class HomeState(
    val myNickname: String = "",
    val partnerNickname: String = "",
    val daysTogether: Int = 0,
    val shareMessage: String = "",
    val todos: List<TodoState> = emptyList(),
    val isShowBottomSheet: Boolean = false,
    val isLoading: Boolean = false
) : UiState {
    val isSetAnniversary: Boolean
        get() = daysTogether != 0
}

@Immutable
data class TodoState(
    val id: Long,
    val title: String,
)