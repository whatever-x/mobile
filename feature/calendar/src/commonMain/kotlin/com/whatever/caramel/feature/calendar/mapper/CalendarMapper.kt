package com.whatever.caramel.feature.calendar.mapper

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.feature.calendar.model.ScheduleCell
import com.whatever.caramel.feature.calendar.model.ScheduleUiModel
import com.whatever.caramel.feature.calendar.util.appOrdianl

internal fun Schedule.toScheduleUiModel(): ScheduleUiModel {
    val type = if (dateTimeInfo.startDateTime.date != dateTimeInfo.endDateTime.date)
        ScheduleUiModel.ScheduleType.MULTI_SCHEDULE
    else
        ScheduleUiModel.ScheduleType.SINGLE_SCHEDULE
    return ScheduleUiModel(
        id = this.id,
        mainText = this.contentData.title.ifEmpty { this.contentData.description },
        type = type,
        description = this.contentData.description,
        contentAssignee = this.contentData.contentAssignee,
    )
}

internal fun Schedule.toScheduleCell(): ScheduleCell.CellUiModel {
    return ScheduleCell.CellUiModel(
        base = this.toScheduleUiModel()
    )
}

internal fun Holiday.toScheduleUiModel(): ScheduleUiModel {
    return ScheduleUiModel(
        mainText = this.name,
        type = ScheduleUiModel.ScheduleType.HOLIDAY,
    )
}

internal fun Holiday.toScheduleCell(): ScheduleCell.CellUiModel {
    return ScheduleCell.CellUiModel(
        base = this.toScheduleUiModel(),
        rowStartIndex = this.date.dayOfWeek.appOrdianl,
        rowEndIndex = this.date.dayOfWeek.appOrdianl,
    )
}

internal fun Anniversary.toScheduleUiModel(): ScheduleUiModel {
    return ScheduleUiModel(
        mainText = this.label,
        type = ScheduleUiModel.ScheduleType.ANNIVERSARY,
    )
}

internal fun Anniversary.toScheduleCell(): ScheduleCell.CellUiModel {
    return ScheduleCell.CellUiModel(
        base = this.toScheduleUiModel(),
        rowStartIndex = this.date.dayOfWeek.appOrdianl,
        rowEndIndex = this.date.dayOfWeek.appOrdianl,
    )
}