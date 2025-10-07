package com.whatever.caramel.feature.calendar.model

import kotlinx.collections.immutable.toImmutableList

data class CalendarBottomSheet(
    val totalList: List<CalendarUiModel> = emptyList(),
) {
    val scheduleList =
        totalList
            .filter { it.type == CalendarUiModel.ScheduleType.SINGLE_SCHEDULE || it.type == CalendarUiModel.ScheduleType.MULTI_SCHEDULE }
            .toImmutableList()

    val holidayList =
        totalList.filter { it.type == CalendarUiModel.ScheduleType.HOLIDAY }.toImmutableList()

    val anniversaryList =
        totalList.filter { it.type == CalendarUiModel.ScheduleType.ANNIVERSARY }.toImmutableList()
}
