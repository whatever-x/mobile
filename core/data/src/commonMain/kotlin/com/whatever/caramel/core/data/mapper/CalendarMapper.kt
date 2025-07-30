package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentMetaData
import com.whatever.caramel.core.remote.dto.calendar.CalendarDetailResponse
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.dto.calendar.response.GetScheduleResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun CalendarDetailResponse.toSchedules(): List<Schedule> =
    this.calendarResult.scheduleList.map {
        Schedule(
            id = it.scheduleId,
            startDate = LocalDateTime.parse(it.startDateTime),
            endDate = LocalDateTime.parse(it.endDateTime),
            tagList = TODO(), // @@@ 스케쥴 목록 획득 시 스케쥴에 포함된 태그들고 함께 내려주게끔 변경 요청
            metaData = ContentMetaData(
                title = it.title ?: "",
                description = it.description ?: "",
                contentAssignee = ContentAssignee.valueOf(value = it.contentAssignee.name),
                isCompleted = it.isCompleted,
            )
        )
    }

// 스케쥴 생성시 사용되던 확장 함수. 사용할 필요 없음
//internal fun CreateScheduleResponse.toScheduleMetaData(): ScheduleMetadata =
//    ScheduleMetadata(
//        contentId = this.contentId,
//        contentType = this.contentType,
//    )

fun HolidayDetailListResponse.toHolidays(): List<Holiday> =
    this.holidayList.map {
        Holiday(
            id = it.id,
            date = LocalDate.parse(it.date),
            name = it.name,
            isHoliday = it.isHoliday,
        )
    }

internal fun GetScheduleResponse.toSchedule(): Schedule =
    with(scheduleDetail) {
        Schedule(
            id = scheduleId,
            startDate = LocalDateTime.parse(startDateTime),
            endDate = LocalDateTime.parse(endDateTime),
            tagList = tags.map { it.toTag() },
            metaData = ContentMetaData(
                title = title ?: "",
                description = description ?: "",
                contentAssignee = ContentAssignee.valueOf(contentAssignee.name),
                isCompleted = isCompleted,
            )
        )
//        ScheduleDetail(
//            scheduleId = scheduleId,
//            startDateTime = startDateTime,
//            endDateTime = endDateTime,
//            startDateTimezone = startDateTimezone,
//            endDateTimezone = endDateTimezone,
//            isCompleted = isCompleted,
//            parentScheduleId = parentScheduleId,
//            title = title ?: "",
//            description = description ?: "",
//            tags = tags.map { it.toTag() },
//            contentAssignee = ContentAssignee.valueOf(contentAssignee.name),
//        )
    }
