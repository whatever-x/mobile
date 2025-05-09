package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.calendar.HolidayList
import com.whatever.caramel.core.domain.vo.calendar.TodoList
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

data class CalendarState(
    val year: Int,
    val month: Month,
    val currentDateList: List<LocalDate>,
    val today: LocalDate = DateUtil.today(),
    val isShownDateSelectDropDown: Boolean = false,
    val bottomSheetState: BottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
    val schedules: List<TodoList> = emptyList(),
    val holidays: List<HolidayList> = emptyList()
) : UiState {
    val bottomSheetList = schedules.map {
        CalendarItem.TodoItem(
            date = it.date,
            todos = it.todos
        )
    } + holidays.map { CalendarItem.HolidayItem(date = it.date, holidays = it.holidays) }
}

enum class BottomSheetState {
    HIDDEN,
    EXPANDED,
    PARTIALLY_EXPANDED
}


sealed interface CalendarItem {
    data class TodoItem(val date: LocalDate, val todos: List<Todo>) : CalendarItem
    data class HolidayItem(val date: LocalDate, val holidays: List<Holiday>) : CalendarItem
}