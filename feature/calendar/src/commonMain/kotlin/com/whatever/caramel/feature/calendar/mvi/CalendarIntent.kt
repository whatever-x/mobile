package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface CalendarIntent : UiIntent {
    data object ClickCreateTodoButton : CalendarIntent
    data object ClickTodoCard : CalendarIntent
    data object ClickDatePicker : CalendarIntent
    data object ClickTodo : CalendarIntent
    data class SelectDate(val year : Int, val month : Int) : CalendarIntent
    data class SelectDay(val day : CalendarDayModel) : CalendarIntent
}