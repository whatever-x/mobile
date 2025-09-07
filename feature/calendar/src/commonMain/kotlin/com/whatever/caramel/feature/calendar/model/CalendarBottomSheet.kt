package com.whatever.caramel.feature.calendar.model

import kotlinx.datetime.LocalDate

data class CalendarBottomSheet(
    val date : LocalDate,
    val totalList: List<CalendarUiModel> = emptyList(),
) {
    val holidayList : List<CalendarUiModel>
        get() = totalList.filter { it.type == CalendarUiModel.ScheduleType.HOLIDAY }

    val anniversaryList : List<CalendarUiModel>
        get() = totalList.filter { it.type == CalendarUiModel.ScheduleType.ANNIVERSARY }

    val scheduleList : List<CalendarUiModel>
        get() = totalList.filter { it.type == CalendarUiModel.ScheduleType.MULTI_SCHEDULE || it.type == CalendarUiModel.ScheduleType.SINGLE_SCHEDULE}
}