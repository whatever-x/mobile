package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.vo.calendar.CalendarYear
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDate

// TODO : 캐싱 작업 필요
class GetCalendarOfYearUseCase(
    private val coupleRepository: CoupleRepository,
    private val calendarRepository: CalendarRepository,
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(
        forceUpdate: Boolean,
        year: Int,
    ): CalendarYear =
        coroutineScope {
            val coupleId = coupleRepository.readCoupleId()
            val startDate = LocalDate(year, 1, 1)
            val endDate = LocalDate(year, 12, 31)

            val scheduleList =
                async {
                    scheduleRepository.getScheduleList(
                        startDate = startDate,
                        endDate = endDate,
                    )
                }
            val holidayList =
                async {
                    calendarRepository.getHolidayList(
                        year = year,
                    )
                }
            val anniversaryList =
                async {
                    calendarRepository.getAnniversaryList(
                        coupleId = coupleId,
                        startDate = startDate,
                        endDate = endDate,
                    )
                }
            CalendarYear(
                scheduleList = scheduleList.await(),
                holidayList = holidayList.await(),
                anniversaryList = anniversaryList.await(),
            )
        }
}
