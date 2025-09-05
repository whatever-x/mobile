package com.whatever.caramel.feature.calendar.model

import kotlinx.datetime.LocalDate

data class ScheduleBottomSheet(
    val date : LocalDate,
    val scheduleList: List<ScheduleUiModel> = emptyList(),
)