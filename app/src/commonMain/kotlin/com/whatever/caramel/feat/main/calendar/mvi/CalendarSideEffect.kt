package com.whatever.caramel.feat.main.calendar.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface CalendarSideEffect : UiSideEffect {

    data object NavigateToCreateTodo : CalendarSideEffect

    data object NavigateToTodoDetail : CalendarSideEffect

}