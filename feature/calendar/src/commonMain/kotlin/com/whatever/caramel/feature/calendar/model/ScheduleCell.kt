package com.whatever.caramel.feature.calendar.model

import kotlinx.datetime.Month

data class ScheduleCell(
    val year: Int,
    val month: Month,
    val weekendIndex: Int,
    val scheduleList: List<CellUiModel> = emptyList(),
) {
    data class CellUiModel(
        val base: ScheduleUiModel,
        val rowStartIndex: Int = 0,
        val rowEndIndex: Int = 0,
        val columnIndex: Int= 0,
    )
}