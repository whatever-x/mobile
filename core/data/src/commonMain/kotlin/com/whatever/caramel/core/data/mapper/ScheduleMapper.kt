package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.response.GetScheduleResponse
import kotlinx.datetime.LocalDateTime

internal fun CalendarDetailResponse.toScheduleList(): List<Schedule> =
    this.calendarResult.scheduleList.map {
        Schedule(
            id = it.scheduleId,
            contentData = ContentData(
                title = it.title ?: "",
                description = it.description ?: "",
                isCompleted = it.isCompleted,
                contentAssignee = ContentAssignee.valueOf(value = it.contentAssignee.name),
            ),
            dateTimeInfo = DateTimeInfo(
                startDateTime = LocalDateTime.parse(it.startDateTime),
                startTimezone = it.startDateTimezone,
                endDateTime = LocalDateTime.parse(it.endDateTime),
                endTimezone = it.endDateTimezone,
            ),
            tagList = emptyList()
        )
    }

internal fun GetScheduleResponse.toSchedule(): Schedule =
    with(this.scheduleDetail) {
        Schedule(
            id = scheduleId,
            contentData = ContentData(
                title = title ?: "",
                description = description ?: "",
                isCompleted = isCompleted,
                contentAssignee = ContentAssignee.valueOf(value = contentAssignee.name),
            ),
            dateTimeInfo = DateTimeInfo(
                startDateTime = LocalDateTime.parse(startDateTime),
                startTimezone = startDateTimezone,
                endDateTime = LocalDateTime.parse(endDateTime),
                endTimezone = endDateTimezone,
            ),
            tagList = this@toSchedule.tags.toTagList()
        )
    }
