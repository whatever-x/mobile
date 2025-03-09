package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.viewmodel.UiState

data class CalendarState(
    val calendar: CalendarModel? = null
) : UiState