package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface CalendarIntent : UiIntent {
    data object SwipeLeftCalendar : CalendarIntent
    data object SwipeRightCalendar : CalendarIntent
    data object OpenCalendarDatePicker : CalendarIntent
    data class DismissCalendarDatePicker(val year : Int, val monthNumber : Int) : CalendarIntent
    data class ClickAddScheduleButton(val date : String) : CalendarIntent
    data class ClickTodoItem(val id : Long) : CalendarIntent
    data class ClickTodoUrl(val url : String?) : CalendarIntent
    data class ToggleCalendarBottomSheet(val sheetState: BottomSheetState) : CalendarIntent
}