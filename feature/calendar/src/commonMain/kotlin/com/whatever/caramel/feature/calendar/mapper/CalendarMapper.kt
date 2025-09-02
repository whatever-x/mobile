package com.whatever.caramel.feature.calendar.mapper

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.feature.calendar.mvi.CalendarScheduleType
import com.whatever.caramel.feature.calendar.mvi.ScheduleUiModel

internal fun Schedule.toScheduleUiModel(): ScheduleUiModel {
    val type = if (dateTimeInfo.startDateTime.date != dateTimeInfo.endDateTime.date)
        CalendarScheduleType.MULTI_SCHEDULE
    else
        CalendarScheduleType.SINGLE_SCHEDULE

    // index는 copy로 수정
    return ScheduleUiModel(
        id = this.id,
        mainText = this.contentData.title.ifEmpty { this.contentData.description },
        rowStartIndex = 0,
        rowEndIndex = 0,
        type = type,
        description = this.contentData.description,
        contentAssignee = this.contentData.contentAssignee,
        columnIndex = 0
    )
}

internal fun Holiday.toScheduleUiModel(): ScheduleUiModel {
    val dayOfWeekIndex = (this.date.dayOfWeek.ordinal + 1) % 7

    return ScheduleUiModel(
        mainText = this.name,
        type = CalendarScheduleType.HOLIDAY,
        rowStartIndex = dayOfWeekIndex,
        rowEndIndex = dayOfWeekIndex,
        columnIndex = 0
    )
}

internal fun Anniversary.toScheduleUiModel(): ScheduleUiModel {
    val dayOfWeekIndex = (this.date.dayOfWeek.ordinal + 1) % 7

    return ScheduleUiModel(
        mainText = this.label,
        type = CalendarScheduleType.ANNIVERSARY,
        rowStartIndex = dayOfWeekIndex,
        rowEndIndex = dayOfWeekIndex,
        columnIndex = 0
    )
}