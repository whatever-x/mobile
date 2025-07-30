package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toHolidays
import com.whatever.caramel.core.data.mapper.toSchedule
import com.whatever.caramel.core.data.mapper.toSchedules
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.content.schedule.EditScheduleParameter
import com.whatever.caramel.core.domain.vo.content.schedule.CreateScheduleParameter
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto

class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource,
) : CalendarRepository {
    override suspend fun createSchedule(parameter: CreateScheduleParameter) { // @@@ : ScheduleMetadata { 스케쥴 생성 시 반환값이 필요 없음
        val request =
            CreateScheduleRequest(
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                startDateTime = parameter.startDateTime,
                startTimeZone = parameter.startTimeZone,
                endDateTime = parameter.endDateTime,
                endTimeZone = parameter.endTimeZone,
                tagIds = parameter.tagIds,
                contentAssignee = ContentAssigneeDto.valueOf(value = parameter.contentAssignee.name),
            ) // @@@ Reuqest Mapper 구현
        return safeCall {
            remoteCalendarDataSource.createSchedule(request) // @@@ .toScheduleMetaData() 반환값이 필요 없으므로 확장 함수 제거
        }
    }

    override suspend fun updateSchedule(
        scheduleId: Long,
        parameter: EditScheduleParameter,
    ) {
        val request =
            UpdateScheduleRequest(
                selectedDate = parameter.selectedDate,
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                startDateTime = parameter.dateTimeInfo?.startDateTime,
                startTimeZone = parameter.dateTimeInfo?.startTimezone,
                endDateTime = parameter.dateTimeInfo?.endDateTime,
                endTimeZone = parameter.dateTimeInfo?.endTimezone,
                tagIds = parameter.tagIds,
                contentAssignee = ContentAssigneeDto.valueOf(value = parameter.contentAssignee.name),
            )
        safeCall {
            remoteCalendarDataSource.updateSchedule(scheduleId, request)
        }
    }

    override suspend fun deleteSchedule(scheduleId: Long) {
        safeCall {
            remoteCalendarDataSource.deleteSchedule(scheduleId)
        }
    }

    override suspend fun getTodos(
        startDate: String,
        endDate: String,
        userTimezone: String?,
    ): List<Schedule> =
        safeCall {
            remoteCalendarDataSource
                .getSchedules(
                    startDate = startDate,
                    endDate = endDate,
                    userTimeZone = userTimezone,
                ).toSchedules()
        }

    override suspend fun getHolidays(year: Int): List<Holiday> =
        safeCall {
            val yearString = year.toString()
            remoteCalendarDataSource.getHolidaysByYear(year = yearString).toHolidays()
        }

    override suspend fun getSchedule(scheduleId: Long): Schedule =
        safeCall {
            val response = remoteCalendarDataSource.getScheduleDetail(scheduleId)
            response.toSchedule()
        }
}
