package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.couple.Anniversary
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class CalendarState(
    val year: Int,
    val month: Month,
    val currentDateList: List<LocalDate>,
    val pageIndex: Int,
    val today: LocalDate = DateUtil.today(),
    val selectedDate: LocalDate = DateUtil.today(),
    val pickerDate: DateUiState = DateUiState.currentDate(),
    val isShowDatePicker: Boolean = false,
    val bottomSheetState: BottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
    val monthSchedules: List<DaySchedule> = emptyList(),
    val isRefreshing: Boolean = false,
    val isBottomSheetDragging: Boolean = false,
) : UiState

enum class BottomSheetState {
    HIDDEN,
    EXPANDED,
    PARTIALLY_EXPANDED,
}

data class DaySchedule(
    val date: LocalDate,
    val todos: List<Todo> = emptyList(),
    val holidays: List<Holiday> = emptyList(),
    val anniversaries: List<Anniversary> = emptyList(),
) {
    val totalScheduleCount: Int
        get() = todos.size + holidays.size + anniversaries.size
}
