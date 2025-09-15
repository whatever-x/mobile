package com.whatever.caramel.feature.calendar.mapper

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.feature.calendar.model.CalendarCellUiModel
import com.whatever.caramel.feature.calendar.model.CalendarUiModel
import com.whatever.caramel.feature.calendar.util.appOrdianl

internal fun Schedule.toScheduleUiModel(originalScheduleSize: Long): CalendarUiModel {
    val type =
        if (dateTimeInfo.startDateTime.date != dateTimeInfo.endDateTime.date) {
            CalendarUiModel.ScheduleType.MULTI_SCHEDULE
        } else {
            CalendarUiModel.ScheduleType.SINGLE_SCHEDULE
        }
    return CalendarUiModel(
        id = this.id,
        mainText = this.contentData.title.ifEmpty { this.contentData.description },
        type = type,
        originalScheduleSize = originalScheduleSize,
        description = this.contentData.description,
        contentAssignee = this.contentData.contentAssignee,
    )
}

internal fun Schedule.toScheduleCell(originalScheduleSize: Long): CalendarCellUiModel =
    CalendarCellUiModel(
        base = this.toScheduleUiModel(originalScheduleSize),
    )

internal fun Holiday.toScheduleUiModel(): CalendarUiModel =
    CalendarUiModel(
        mainText = this.name,
        type = CalendarUiModel.ScheduleType.HOLIDAY,
        originalScheduleSize = 1,
    )

internal fun Holiday.toScheduleCell(): CalendarCellUiModel =
    CalendarCellUiModel(
        base = this.toScheduleUiModel(),
        rowStartIndex = this.date.dayOfWeek.appOrdianl,
        rowEndIndex = this.date.dayOfWeek.appOrdianl,
    )

internal fun Anniversary.toScheduleUiModel(): CalendarUiModel =
    CalendarUiModel(
        mainText = this.label,
        type = CalendarUiModel.ScheduleType.ANNIVERSARY,
        originalScheduleSize = 1,
    )

internal fun Anniversary.toScheduleCell(): CalendarCellUiModel =
    CalendarCellUiModel(
        base = this.toScheduleUiModel(),
        rowStartIndex = this.date.dayOfWeek.appOrdianl,
        rowEndIndex = this.date.dayOfWeek.appOrdianl,
    )
