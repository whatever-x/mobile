package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.vo.calendar.TodoList
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class CalendarState(
    val year: Int,
    val month: Month,
    val currentDateList: List<LocalDate>,
    val today: LocalDate = DateUtil.today(),
    val isShownDateSelectDropDown: Boolean = false,
    val bottomSheetState: BottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
    val schedules: List<TodoList> = emptyList()
) : UiState

enum class BottomSheetState {
    HIDDEN,
    EXPANDED,
    PARTIALLY_EXPANDED
}

