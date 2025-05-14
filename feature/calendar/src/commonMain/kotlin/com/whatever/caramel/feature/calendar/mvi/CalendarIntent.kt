package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiIntent
import kotlinx.datetime.LocalDate

sealed interface CalendarIntent : UiIntent {
    data object SwipeLeftCalendar : CalendarIntent
    data object SwipeRightCalendar : CalendarIntent
    data object OpenCalendarDatePicker : CalendarIntent
    data class DismissCalendarDatePicker(val year : Int, val monthNumber : Int) : CalendarIntent
    data class ClickAddScheduleButton(val date : String) : CalendarIntent
    data class ClickTodoItemInBottomSheet(val todoId : Long) : CalendarIntent
    data class ClickTodoUrl(val url : String?) : CalendarIntent
    data class ToggleCalendarBottomSheet(val sheetState: BottomSheetState) : CalendarIntent
    data class ClickCalendarCell(val selectedDate: LocalDate) : CalendarIntent
    data class ClickTodoItemInCalendar(val todoId : Long) : CalendarIntent
    data class UpdatePageIndex(val index : Int) : CalendarIntent
}