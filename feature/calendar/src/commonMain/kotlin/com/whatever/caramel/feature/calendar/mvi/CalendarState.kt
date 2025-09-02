package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.serialization.Serializable

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
    val testUiModelList: List<CalendarSchedule> = emptyList(),
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

@Serializable
data class CalendarSchedule(
    val year: Int,
    val month: Month,
    val weekendIndex: Int,
    val uiModelList: List<ScheduleUiModel> = emptyList(),
)

@Serializable
data class ScheduleUiModel(
    val id: Long? = null,
    val mainText: String,
    val rowStartIndex: Int,
    val rowEndIndex: Int,
    val columnIndex: Int,
    val type: CalendarScheduleType,
    val description: String? = null,
    val contentAssignee: ContentAssignee? = null,
)

enum class CalendarScheduleType(val priority: Int) {
    MULTI_SCHEDULE(1),
    HOLIDAY(2),
    ANNIVERSARY(3),
    SINGLE_SCHEDULE(4),
    ;
}