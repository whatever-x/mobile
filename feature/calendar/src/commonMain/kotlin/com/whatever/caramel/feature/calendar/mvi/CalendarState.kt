package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import com.whatever.caramel.feature.calendar.model.ScheduleBottomSheet
import com.whatever.caramel.feature.calendar.model.ScheduleCell
import com.whatever.caramel.feature.calendar.model.ScheduleYearCache
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class CalendarState(
    val isInitialized: Boolean = true,
    val year: Int,
    val month: Month,
    val currentDateList: List<LocalDate>,
    val pageIndex: Int,
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
    val today: LocalDate = DateUtil.today()

    val monthBottomSheetList: ImmutableList<ScheduleBottomSheet>
        get() = scheduleBottomSheetList.filter { it.date.month == month }.toImmutableList()

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

