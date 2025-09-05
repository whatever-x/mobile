package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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
    val isRefreshing: Boolean = false,
    val isBottomSheetDragging: Boolean = false,
    val scheduleCellList: List<ScheduleCell> = emptyList(),
    val scheduleBottomSheetList: List<ScheduleBottomSheet> = emptyList(),
    val yearCacheList: List<ScheduleYearCache> = emptyList(),
) : UiState {
    val monthBottomSheetList: ImmutableList<ScheduleBottomSheet>
        get() = scheduleBottomSheetList.filter { it.dateTime.date.month == month }.toImmutableList()

    val monthCellList: ImmutableList<ScheduleCell>
        get() = scheduleCellList.filter { it.year == year && it.month == month }.toImmutableList()

    val isBottomSheetTopDescVisible
        get() = !isBottomSheetDragging && bottomSheetState == BottomSheetState.PARTIALLY_EXPANDED
}

enum class BottomSheetState {
    HIDDEN,
    EXPANDED,
    PARTIALLY_EXPANDED,
}

data class ScheduleUiModel(
    val id: Long? = null,
    val mainText: String,
    val type: CalendarScheduleType,
    val description: String? = null,
    val contentAssignee: ContentAssignee? = null,
)

data class ScheduleCell(
    val year: Int,
    val month: Month,
    val weekendIndex: Int,
    val scheduleList: List<ScheduleCellUiModel> = emptyList(),
)

data class ScheduleCellUiModel(
    val base: ScheduleUiModel,
    val rowStartIndex: Int,
    val rowEndIndex: Int,
    val columnIndex: Int,
)

data class ScheduleBottomSheet(
    val base: ScheduleUiModel,
    val dateTime: LocalDateTime,
)

data class ScheduleYearCache(
    val year: Int,
    val totalList: List<ScheduleMemoryModel> = emptyList(),
)

data class ScheduleMemoryModel(
    val localDate: LocalDate,
    val scheduleList: List<Schedule> = emptyList(),
    val anniversary: List<Anniversary> = emptyList(),
    val holiday: List<Holiday> = emptyList(),
)

enum class CalendarScheduleType(val priority: Int) {
    MULTI_SCHEDULE(1),
    HOLIDAY(2),
    ANNIVERSARY(3),
    SINGLE_SCHEDULE(4),
    ;
}