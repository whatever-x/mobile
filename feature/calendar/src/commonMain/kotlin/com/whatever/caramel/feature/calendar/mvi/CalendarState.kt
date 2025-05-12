package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class CalendarState(
    val year: Int,
    val month: Month,
    val currentDateList: List<LocalDate>,
    val today: LocalDate = DateUtil.today(),
    val selectedDate: LocalDate = DateUtil.today(),
    val isShownDateSelectDropDown: Boolean = false,
    val bottomSheetState: BottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
    val schedulesByDate: List<Schedule> = emptyList(),
    val schedulesByPriority: List<Schedule> = emptyList(),
) : UiState

enum class BottomSheetState {
    HIDDEN,
    EXPANDED,
    PARTIALLY_EXPANDED
}

sealed interface Schedule {
    val date: LocalDate
    val listSize: Int
        get() {
            return when (this) {
                is Holidays -> 0
                is Todos -> todos.size
            }
        }

    data class Todos(override val date: LocalDate, val todos: List<Todo>) : Schedule
    data class Holidays(override val date: LocalDate, val holidays: List<Holiday>) : Schedule

}