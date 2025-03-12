package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.viewmodel.UiState

data class CalendarState(
    val isLoading: Boolean = false,
    val calendar : CalendarModel? = null,
    val selectedYear : Int? = null,
    val selectedMonth : Int? = null,
    val selectedDay : Int? = null,
    val isDatePickerOpen : Boolean = false
) : UiState