package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState

class CalendarViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarState, CalendarSideEffect, CalendarIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): CalendarState {
        return CalendarState()
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.ClickTodoCard -> postSideEffect(CalendarSideEffect.NavigateToCreateTodo)
            is CalendarIntent.ClickCreateTodoButton -> postSideEffect(CalendarSideEffect.NavigateToTodoDetail)
        }
    }

}