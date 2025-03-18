package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.domain.model.calendar.CalendarTodoModel
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.DayOfWeek

data class CalendarState(
    val isLoading: Boolean = false,
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0,
    val today: Int = 1,
    val calendarDays: List<CalendarDayState> = emptyList(),
    val datePickerState: CalendarDatePickerState = CalendarDatePickerState(),
    val todoSheetState: CalendarTodoSheetState = CalendarTodoSheetState(),
) : UiState {
    val totalPage: Int = (CalendarModel.YEAR_RANGE.count() * CalendarModel.MONTH_RANGE.count())
    val currentPage: Int
        get() {
            val yearPageCount = (this.selectedYear - CalendarModel.MIN_YEAR) * 12
            val monthIndex = this.selectedMonth - 1

            return yearPageCount + monthIndex
        }

    fun calcYearAndMonthByPageCount(pageCount: Int): Pair<Int, Int> {
        val year = CalendarModel.MIN_YEAR + (pageCount / 12)
        val month = (pageCount % 12) + 1
        return Pair(year, month)
    }

    fun calcWeekend(): Int {
        val firstDayOfWeek = getFirstDayOfWeekendOrdinal()
        return (calendarDays.size + firstDayOfWeek + (CalendarModel.getWeekCnt() - 1)) / CalendarModel.getWeekCnt()
    }

    fun isFirstEmptyWeekendEmptyDay(
        weekend: Int,
    ): Boolean {
        val firstDayOfWeek = getFirstDayOfWeekendOrdinal()
        val emptyDay = (weekend * CalendarModel.getWeekCnt() - firstDayOfWeek - 1)
        return emptyDay == -(CalendarModel.getWeekCnt())
    }

    fun getFirstDayOfWeekendOrdinal(): Int {
        return calendarDays.firstOrNull()?.dayOfWeek?.ordinal ?: 0
    }
}

data class CalendarDatePickerState(
    val isOpen: Boolean = false,
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0,
)

data class CalendarTodoSheetState(
    val isOpen: Boolean = false,
    val month: Int = 0,
    val dayList: List<CalendarDayModel> = emptyList(),
)

data class CalendarDayState(
    val isFocused: Boolean = false,
    val day: Int = 0,
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val isHoliday: Boolean = false,
    val isSaturday: Boolean = false,
    val todos: List<CalendarTodoModel> = emptyList(),
)