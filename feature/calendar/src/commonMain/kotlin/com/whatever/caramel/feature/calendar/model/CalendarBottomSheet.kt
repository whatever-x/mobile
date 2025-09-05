package com.whatever.caramel.feature.calendar.model

import kotlinx.datetime.LocalDate

data class CalendarBottomSheet(
    val date : LocalDate,
    val scheduleList: List<CalendarUiModel> = emptyList(),
)