package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
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
    val yearScheduleList: List<DaySchedule> = emptyList(),
    val cachedYearScheduleList: Map<Int, List<DaySchedule>> = emptyMap(),
    val isRefreshing: Boolean = false,
    val isBottomSheetDragging: Boolean = false,
) : UiState {
    val monthScheduleList: List<DaySchedule>
        get() = yearScheduleList.filter { it.date.month == month }

    val isBottomSheetTopDescVisible
        get() = !isBottomSheetDragging && bottomSheetState == BottomSheetState.PARTIALLY_EXPANDED
}

enum class BottomSheetState {
    HIDDEN,
    EXPANDED,
    PARTIALLY_EXPANDED,
}

data class DaySchedule(
    val date: LocalDate,
    val scheduleList: List<Schedule> = emptyList(),
    val holidayList: List<Holiday> = emptyList(),
    val anniversaryList: List<Anniversary> = emptyList(),
) {
    val totalScheduleCount: Int
        get() = scheduleList.size + holidayList.size + anniversaryList.size
}
