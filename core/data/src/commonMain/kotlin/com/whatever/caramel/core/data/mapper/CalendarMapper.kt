package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail
import com.whatever.caramel.core.domain.vo.calendar.ScheduleMetadata
import com.whatever.caramel.core.domain.vo.content.ContentRole
import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.dto.calendar.response.CreateScheduleResponse
import com.whatever.caramel.core.remote.dto.calendar.response.GetScheduleResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun CalendarDetailResponse.toTodo(): List<Todo> =
    this.calendarResult.scheduleList.map {
        Todo(
            id = it.scheduleId,
            startDate = LocalDateTime.parse(it.startDateTime),
            endDate = LocalDateTime.parse(it.endDateTime),
            title = it.title ?: "",
            description = it.description ?: "",
            contentRole = ContentRole.MY // FIXME : API 연동 시 remote와 연결 필요
        )
    }

internal fun CreateScheduleResponse.toScheduleMetaData(): ScheduleMetadata =
    ScheduleMetadata(
        contentId = this.contentId,
        contentType = this.contentType,
    )

fun HolidayDetailListResponse.toHoliday(): List<Holiday> =
    this.holidayList.map {
        Holiday(
            id = it.id,
            date = LocalDate.parse(it.date),
            name = it.name,
            isHoliday = it.isHoliday,
        )
    }

internal fun GetScheduleResponse.toScheduleDetailVO(): ScheduleDetail =
    with(scheduleDetail) {
        ScheduleDetail(
            scheduleId = scheduleId,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            startDateTimezone = startDateTimezone,
            endDateTimezone = endDateTimezone,
            isCompleted = isCompleted,
            parentScheduleId = parentScheduleId,
            title = title,
            description = description,
            tags = tags.map { it.toTag() },
        )
    }
