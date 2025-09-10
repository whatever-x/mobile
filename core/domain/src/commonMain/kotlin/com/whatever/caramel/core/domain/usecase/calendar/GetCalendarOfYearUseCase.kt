package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.vo.calendar.CalendarYear
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.datetime.LocalDate

class GetCalendarOfYearUseCase(
    private val coupleRepository: CoupleRepository,
    private val calendarRepository: CalendarRepository,
    private val scheduleRepository: ScheduleRepository,
) {
    suspend operator fun invoke(
        forceUpdate: Boolean,
        year: Int,
    ): CalendarYear {
        val coupleId = coupleRepository.readCoupleId()
        val startDate = LocalDate(year, 1, 1)
        val endDate = LocalDate(year, 12, 31)

        // TODO : 캘린더 레포에서 캐싱데이터 가져오기

        val newData =
            CoroutineScope(Dispatchers.IO).async {
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
        return newData.await()
    }
}
