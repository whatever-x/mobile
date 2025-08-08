package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toAnniversary
import com.whatever.caramel.core.data.mapper.toHolidayList
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDataSource

class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource,
    private val remoteCoupleDataSource: RemoteCoupleDataSource,
) : CalendarRepository {
    override suspend fun getHolidayList(year: Int): List<Holiday> =
        safeCall {
            val yearString = year.toString()
            remoteCalendarDataSource.fetchHolidayListByYear(year = yearString).toHolidayList()
        }

    override suspend fun getAnniversaryList(
        coupleId: Long,
        startDate: String,
        endDate: String,
    ): List<Anniversary> =
        safeCall {
            remoteCoupleDataSource
                .fetchAnniversaryList(
                    coupleId = coupleId,
                    startDate = startDate,
                    endDate = endDate,
                ).toAnniversary()
        }
}
