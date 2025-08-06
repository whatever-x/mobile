package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toSchedule
import com.whatever.caramel.core.data.mapper.toScheduleList
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.remote.datasource.RemoteScheduleDataSource
import com.whatever.caramel.core.remote.dto.calendar.request.CreateScheduleRequest
import com.whatever.caramel.core.remote.dto.calendar.request.UpdateScheduleRequest
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto

class ScheduleRepositoryImpl(
    private val remoteScheduleDataSource: RemoteScheduleDataSource,
): ScheduleRepository {
    override suspend fun createSchedule(parameter: ScheduleParameter) {
        val request = CreateScheduleRequest(
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
            remoteScheduleDataSource.createSchedule(request = request)
        }
    }

    override suspend fun updateSchedule(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    ) {
        val request = UpdateScheduleRequest(
            selectedDate = parameter.selectedDate,
            title = parameter.title,
            description = parameter.description,
            isCompleted = parameter.isCompleted,
            startDateTime = parameter.dateTimeInfo?.startDateTime.toString(),
            startTimeZone = parameter.dateTimeInfo?.startTimezone,
            endDateTime = parameter.dateTimeInfo?.endDateTime.toString(),
            endTimeZone = parameter.dateTimeInfo?.endTimezone,
            tagIds = parameter.tagIds,
            contentAssignee = ContentAssigneeDto.valueOf(value = parameter.contentAssignee.name),
        )

        safeCall {
            remoteScheduleDataSource.updateSchedule(
                scheduleId = scheduleId,
                updateScheduleRequest = request
            )
        }
    }

    override suspend fun deleteSchedule(scheduleId: Long) {
        safeCall {
            remoteScheduleDataSource.deleteSchedule(scheduleId)
        }
    }

    override suspend fun getScheduleList(
        startDate: String,
        endDate: String,
        userTimezone: String?
    ): List<Schedule> =
        safeCall {
            remoteScheduleDataSource
                .fetchScheduleList(
                    startDate = startDate,
                    endDate = endDate,
                    userTimeZone = userTimezone,
                ).toScheduleList()
        }

    override suspend fun getSchedule(scheduleId: Long): Schedule =
        safeCall {
            val response = remoteScheduleDataSource.fetchSchedule(scheduleId = scheduleId)
            response.toSchedule()
        }
}