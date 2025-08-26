package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.policy.CalendarPolicy.YEAR_RANGE
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.datetime.LocalDate

data class CalendarState(
    val dateUiState: DateUiState = DateUiState.currentDate(),
    val currentDate: LocalDate = DateUtil.today(),
    val isShowDropDownMenu: Boolean = false,
    val isRefreshing: Boolean = false,
) : UiState {

    val page: Int
        get() {
            val index = YEAR_RANGE.indexOf(element = currentDate.year)
            return index * 12 + (currentDate.monthNumber - 1)
        }

}