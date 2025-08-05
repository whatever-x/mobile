package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.response.GetScheduleResponse
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto
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
            tagList = TODO() // @ham2174 TODO : ScheduleApiResult 내부 필드에 TagList 포함 요청
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
            tagList = TODO() // @ham2174 TODO : ScheduleApiResult 내부 필드에 TagList 포함 요청
        )
    }

internal fun ScheduleParameter.toCreateScheduleRequest(): CreateScheduleRequest =
    CreateScheduleRequest(
        title = title,
        description = description,
        isCompleted = isCompleted,
        startDateTime = startDateTime,
        startTimeZone = startTimeZone,
        endDateTime = endDateTime,
        endTimeZone = endTimeZone,
        tagIds = tagIds,
        contentAssignee = ContentAssigneeDto.valueOf(value = contentAssignee.name),
    )

internal fun ScheduleEditParameter.toEditScheduleRequest(): UpdateScheduleRequest =
    UpdateScheduleRequest(
        selectedDate = selectedDate,
        title = title,
        description = description,
        isCompleted = isCompleted,
        startDateTime = dateTimeInfo?.startDateTime.toString(),
        startTimeZone = dateTimeInfo?.startTimezone,
        endDateTime = dateTimeInfo?.endDateTime.toString(),
        endTimeZone = dateTimeInfo?.endTimezone,
        tagIds = tagIds,
        contentAssignee = ContentAssigneeDto.valueOf(value = contentAssignee.name),
    )