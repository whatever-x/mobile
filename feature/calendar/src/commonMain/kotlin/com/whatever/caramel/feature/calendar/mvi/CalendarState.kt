package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class CalendarState(
    val text: String = ""
) : UiState