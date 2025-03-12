package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CalendarSideEffect : UiSideEffect {
    data object NavigateToCreateTodo : CalendarSideEffect
    data class NavigateToTodoDetail(val todoId: Int) : CalendarSideEffect
}