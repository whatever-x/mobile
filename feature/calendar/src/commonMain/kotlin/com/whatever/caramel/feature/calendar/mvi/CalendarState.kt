package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.domain.model.calendar.CalendarTodoModel
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.DayOfWeek

data class CalendarState(
    val isLoading: Boolean = false,
    val selectedYear: Int? = null,
    val selectedMonth: Int? = null,
    val calendarDays: List<CalendarDayState> = emptyList(),
    val datePickerState: CalendarDatePickerState = CalendarDatePickerState(),
    val todoSheetState: CalendarTodoSheetState = CalendarTodoSheetState(),
) : UiState {
    val totalPage: Int = CalendarModel.YEAR_RANGE.count()
    val currentPage: Int
        get() {
            val yearPageCount = (this.selectedYear?.minus(CalendarModel.MIN_YEAR))?.times(12)
            val monthIndex = (this.selectedMonth?.minus(1)) ?: 0

            return yearPageCount?.plus(monthIndex) ?: 0
        }

    fun calcPage(pageCount: Int): Pair<Int, Int> {
        val year = CalendarModel.MIN_YEAR + (pageCount / 12)
        val month = (pageCount % 12) + 1
        return Pair(year, month)
    }

    fun calcWeekend(): Int {
        val firstDayOfWeek = getFirstDayOfWeekend().ordinal
        return (calendarDays.size + firstDayOfWeek + (CalendarModel.getWeekCnt() - 1)) / CalendarModel.getWeekCnt()
    }

    fun isFirstEmptyWeekendEmptyDay(
        weekend: Int,
    ): Boolean {
        val firstDayOfWeek = getFirstDayOfWeekend().ordinal
        val emptyDay = (weekend * CalendarModel.getWeekCnt()) - (firstDayOfWeek - 1)
        return emptyDay == -(CalendarModel.getWeekCnt())
    }

    fun getFirstDayOfWeekend(): DayOfWeek {
        return calendarDays.firstOrNull()?.dayOfWeek ?: DayOfWeek.MONDAY
    }
}

// FIXME : ViewModel 초기화에서 사용되지 않는 State는 interface를 상속안해도 될까?
data class CalendarDatePickerState(
    val isOpen: Boolean = false,
    val selectedYear: Int? = null,
    val selectedMonth: Int? = null,
) : UiState

data class CalendarTodoSheetState(
    val isOpen: Boolean = false,
    val month: Int? = null,
    val dayList: List<CalendarDayModel>? = null,
) : UiState

data class CalendarDayState(
    val isFocused: Boolean = false,
    val day: Int? = null,
    val dayOfWeek: DayOfWeek? = null,
    val isHoliday: Boolean = false,
    val isSaturday: Boolean = false,
    val todos: List<CalendarTodoModel> = emptyList(),
) : UiState