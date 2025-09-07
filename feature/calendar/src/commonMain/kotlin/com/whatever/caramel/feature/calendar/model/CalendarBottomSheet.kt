package com.whatever.caramel.feature.calendar.model

import kotlinx.datetime.LocalDate

data class CalendarBottomSheet(
    val date: LocalDate,
    val totalList: List<BottomSheetUiModel> = emptyList(),
) {
    data class BottomSheetUiModel(
        val scheduleSize: Long,
        val base: CalendarUiModel,
    )

    val holidayList: List<BottomSheetUiModel>
        get() = totalList.filter { it.base.type == CalendarUiModel.ScheduleType.HOLIDAY }

    val anniversaryList: List<BottomSheetUiModel>
        get() = totalList.filter { it.base.type == CalendarUiModel.ScheduleType.ANNIVERSARY }

    val scheduleList: List<BottomSheetUiModel>
        get() =
            totalList.filter {
                it.base.type == CalendarUiModel.ScheduleType.MULTI_SCHEDULE ||
                    it.base.type == CalendarUiModel.ScheduleType.SINGLE_SCHEDULE
            }
}
