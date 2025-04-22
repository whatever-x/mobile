package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class HomeState(
    val daysTogether: Int = 0,
    val shareMessage: String = "",
    val todos: List<TodoState> = emptyList(),
    val isSetAnniversary: Boolean = false,
    val isShowBottomSheet: Boolean = false,
    val isLoading: Boolean = false
) : UiState

@Immutable
data class TodoState(
    val id: Long,
    val title: String,
)