package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiIntent
import kotlinx.datetime.LocalDate

sealed interface CalendarIntent : UiIntent {
    data object Initialize : CalendarIntent

    data object RefreshCalendar : CalendarIntent

    data object ClickDatePicker : CalendarIntent

    data object ClickDatePickerOutSide : CalendarIntent

    data object ClickOutSideBottomSheet : CalendarIntent

    data class ClickAddScheduleButton(
        val date: LocalDate,
    ) : CalendarIntent

    data class ClickTodoItemInBottomSheet(
        val todoId: Long,
    ) : CalendarIntent

    data class ClickTodoUrl(
        val url: String?,
    ) : CalendarIntent

    data class ClickCalendarCell(
        val selectedDate: LocalDate,
    ) : CalendarIntent

    data class ClickTodoItemInCalendar(
        val todoId: Long,
    ) : CalendarIntent

    data class UpdatePageIndex(
        val index: Int,
    ) : CalendarIntent

    data class UpdateSelectPickerYear(
        val year: Int,
    ) : CalendarIntent

    data class UpdateSelectPickerMonth(
        val month: Int,
    ) : CalendarIntent

    data class UpdateCalendarBottomSheet(
        val sheetState: BottomSheetState,
    ) : CalendarIntent

    data class DraggingCalendarBottomSheet(
        val isDragging: Boolean,
    ) : CalendarIntent
}
