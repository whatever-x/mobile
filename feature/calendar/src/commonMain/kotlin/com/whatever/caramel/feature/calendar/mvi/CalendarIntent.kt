package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface CalendarIntent : UiIntent {
    data class ClickTodo(val todoId: Int) : CalendarIntent
    data class SwipeCalendar(val pageIndex : Int) : CalendarIntent
    data class ToggleDate(val year: Int, val month: Int) : CalendarIntent
    data class DismissDatePicker(val year: Int, val month: Int) : CalendarIntent
    data class ToggleDay(val month : Int, val dayList: List<CalendarDayState>, val selectedDay: Int) : CalendarIntent
}