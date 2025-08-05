package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCreateScheduleRequest
import com.whatever.caramel.core.data.mapper.toEditScheduleRequest
import com.whatever.caramel.core.data.mapper.toScheduleList
import com.whatever.caramel.core.data.mapper.toSchedule
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.remote.datasource.RemoteScheduleDataSource

class ScheduleRepositoryImpl(
    private val remoteScheduleDataSource: RemoteScheduleDataSource,
): ScheduleRepository {
    override suspend fun createSchedule(parameter: ScheduleParameter) {
        return safeCall {
            remoteScheduleDataSource.createSchedule(request = parameter.toCreateScheduleRequest())
        }
    }

    override suspend fun updateSchedule(
        scheduleId: Long,
        parameter: ScheduleEditParameter,
    ) {
        safeCall {
            remoteScheduleDataSource.updateSchedule(
                scheduleId = scheduleId,
                updateScheduleRequest = parameter.toEditScheduleRequest()
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