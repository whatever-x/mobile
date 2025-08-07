package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.calendar.AnniversaryOnDate

class GetAnniversaryListUseCase(
    private val coupleRepository: CoupleRepository,
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
    ): List<AnniversaryOnDate> {
        val coupleId = coupleRepository.readCoupleId()
        return calendarRepository.getAnniversaryList(
            coupleId = coupleId,
            startDate = startDate,
            endDate = endDate,
        ).groupBy { it.date }
            .map { (date, anniversaries) -> AnniversaryOnDate(date, anniversaries) }
    }
}