package com.whatever.caramel.feature.calendar.mapper

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.feature.calendar.model.CalendarBottomSheet
import com.whatever.caramel.feature.calendar.model.CalendarCell
import com.whatever.caramel.feature.calendar.model.CalendarUiModel
import com.whatever.caramel.feature.calendar.util.appOrdianl

internal fun Schedule.toScheduleUiModel(): CalendarUiModel {
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
        description = this.contentData.description,
        contentAssignee = this.contentData.contentAssignee,
    )
}

internal fun Schedule.toScheduleCell(): CalendarCell.CellUiModel =
    CalendarCell.CellUiModel(
        base = this.toScheduleUiModel(),
    )

internal fun Schedule.toBottomSheet(existSize: Long): CalendarBottomSheet.BottomSheetUiModel =
    CalendarBottomSheet.BottomSheetUiModel(
        base = this.toScheduleUiModel(),
        scheduleSize = existSize,
    )

internal fun Holiday.toScheduleUiModel(): CalendarUiModel =
    CalendarUiModel(
        mainText = this.name,
        type = CalendarUiModel.ScheduleType.HOLIDAY,
    )

internal fun Holiday.toScheduleCell(): CalendarCell.CellUiModel =
    CalendarCell.CellUiModel(
        base = this.toScheduleUiModel(),
        rowStartIndex = this.date.dayOfWeek.appOrdianl,
        rowEndIndex = this.date.dayOfWeek.appOrdianl,
    )

internal fun Holiday.toBottomSheet(): CalendarBottomSheet.BottomSheetUiModel =
    CalendarBottomSheet.BottomSheetUiModel(
        base = this.toScheduleUiModel(),
        scheduleSize = 1,
    )

internal fun Anniversary.toScheduleUiModel(): CalendarUiModel =
    CalendarUiModel(
        mainText = this.label,
        type = CalendarUiModel.ScheduleType.ANNIVERSARY,
    )

internal fun Anniversary.toScheduleCell(): CalendarCell.CellUiModel =
    CalendarCell.CellUiModel(
        base = this.toScheduleUiModel(),
        rowStartIndex = this.date.dayOfWeek.appOrdianl,
        rowEndIndex = this.date.dayOfWeek.appOrdianl,
    )

internal fun Anniversary.toBottomSheet(): CalendarBottomSheet.BottomSheetUiModel =
    CalendarBottomSheet.BottomSheetUiModel(
        base = this.toScheduleUiModel(),
        scheduleSize = 1,
    )
