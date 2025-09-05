package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import com.whatever.caramel.feature.calendar.model.CalendarBottomSheet
import com.whatever.caramel.feature.calendar.model.CalendarCell
import com.whatever.caramel.feature.calendar.model.CalendarYearCache
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
    val calendarCellList: List<CalendarCell> = emptyList(),
    val calendarBottomSheetList: List<CalendarBottomSheet> = emptyList(),
    val yearCacheList: List<CalendarYearCache> = emptyList(),
) : UiState {
    val today: LocalDate = DateUtil.today()

    val monthBottomSheetList: ImmutableList<CalendarBottomSheet>
        get() = calendarBottomSheetList.filter { it.date.month == month }.toImmutableList()

    val monthCellList: ImmutableList<CalendarCell>
        get() = calendarCellList.filter { it.year == year && it.month == month }.toImmutableList()

    val isBottomSheetTopDescVisible
        get() = !isBottomSheetDragging && bottomSheetState == BottomSheetState.PARTIALLY_EXPANDED
}