package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toHoliday
import com.whatever.caramel.core.data.mapper.toScheduleDetailVO
import com.whatever.caramel.core.data.mapper.toScheduleMetaData
import com.whatever.caramel.core.data.mapper.toTodo
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.params.schedule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.schedule.ScheduleParameter
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto

class ScheduleRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource,
) : ScheduleRepository {
    override suspend fun createSchedule(parameter: ScheduleParameter): ScheduleMetadata {
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
            )
        return safeCall {
            remoteCalendarDataSource.createSchedule(request).toScheduleMetaData()
        }
    }

    override suspend fun updateSchedule(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    ) {
        val request =
            UpdateScheduleRequest(
                selectedDate = parameter.selectedDate,
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                startDateTime = parameter.scheduleDateTime?.startDateTime,
                startTimeZone = parameter.scheduleDateTime?.startTimezone,
                endDateTime = parameter.scheduleDateTime?.endDateTime,
                endTimeZone = parameter.scheduleDateTime?.endTimezone,
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
    ): List<Todo> =
        safeCall {
            remoteCalendarDataSource
                .getSchedules(
                    startDate = startDate,
                    endDate = endDate,
                    userTimeZone = userTimezone,
                ).toTodo()
        }

    override suspend fun getHolidays(year: Int): List<Holiday> =
        safeCall {
            val yearString = year.toString()
            remoteCalendarDataSource.getHolidaysByYear(year = yearString).toHoliday()
        }

    override suspend fun getSchedule(scheduleId: Long): ScheduleDetail =
        safeCall {
            val response = remoteCalendarDataSource.getScheduleDetail(scheduleId)
            response.toScheduleDetailVO()
        }
}
