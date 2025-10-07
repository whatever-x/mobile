package com.whatever.caramel.feature.calendar.model

data class CalendarCellUiModel(
    val base: CalendarUiModel,
    val rowStartIndex: Int = 0,
    val rowEndIndex: Int = 0,
    val columnIndex: Int = 0,
)
