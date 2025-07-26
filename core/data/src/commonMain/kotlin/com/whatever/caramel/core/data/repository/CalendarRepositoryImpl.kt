package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toHoliday
import com.whatever.caramel.core.data.mapper.toScheduleDetailVO
import com.whatever.caramel.core.data.mapper.toScheduleMetaData
import com.whatever.caramel.core.data.mapper.toTodo
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail
import com.whatever.caramel.core.domain.vo.calendar.ScheduleEditParameter
import com.whatever.caramel.core.domain.vo.calendar.ScheduleMetadata
import com.whatever.caramel.core.domain.vo.calendar.ScheduleParameter
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.dto.memo.ContentAsigneeDto

class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource,
) : CalendarRepository {
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
                contentAsignee = ContentAsigneeDto.US, // @ham2174 FIXME : 파라미터로 넘겨받도록 수정
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
                startDateTime = parameter.dateTimeInfo?.startDateTime,
                startTimeZone = parameter.dateTimeInfo?.startTimezone,
                endDateTime = parameter.dateTimeInfo?.endDateTime,
                endTimeZone = parameter.dateTimeInfo?.endTimezone,
                tagIds = parameter.tagIds,
                contentAsignee = ContentAsigneeDto.US, // @ham2174 FIXME : 파라미터로 넘겨받도록 수정
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
            remoteCalendarDataSource.getSchedules(startDate, endDate, userTimezone).toTodo()
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
