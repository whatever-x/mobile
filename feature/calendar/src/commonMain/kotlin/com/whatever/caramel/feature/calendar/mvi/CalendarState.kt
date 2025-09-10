package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import com.whatever.caramel.feature.calendar.model.CalendarBottomSheet
import com.whatever.caramel.feature.calendar.model.CalendarBottomSheetState
import com.whatever.caramel.feature.calendar.model.CalendarCacheModel
import com.whatever.caramel.feature.calendar.model.CalendarCell
import com.whatever.caramel.feature.calendar.model.CalendarCellUiModel
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toImmutableSet
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
    val bottomSheetState: CalendarBottomSheetState = CalendarBottomSheetState.PARTIALLY_EXPANDED,
    val isRefreshing: Boolean = false,
    val isBottomSheetDragging: Boolean = false,
    val calendarCellMap: ImmutableMap<CalendarCell, List<CalendarCellUiModel>> = persistentMapOf(),
    val calendarBottomSheetMap: ImmutableMap<LocalDate, CalendarBottomSheet> = persistentMapOf(),
    val yearCacheMap: LinkedHashMap<Int, CalendarCacheModel> = linkedMapOf(),
) : UiState {

    val monthBottomSheetMap: ImmutableMap<LocalDate, CalendarBottomSheet>
        get() = (calendarBottomSheetMap.filter { it.key.year == year && it.key.month == month }).toImmutableMap()

    val isBottomSheetTopDescVisible
        get() = !isBottomSheetDragging && bottomSheetState == CalendarBottomSheetState.PARTIALLY_EXPANDED

    val yearHolidayDateSet: ImmutableSet<LocalDate>
        get() = (yearCacheMap[year]?.holidayList ?: emptyList()).map { it.date }.toImmutableSet()

    val bottomSheetScrollPosition: Int
        get() =
            monthBottomSheetMap.entries
                .takeWhile { it.key != selectedDate }
                .sumOf { maxOf(1, it.value.scheduleList.size) + 1 }
}
