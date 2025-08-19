package com.whatever.caramel.core.domain.usecase.calendar

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.calendar.Anniversary

class GetAnniversariesInPeriodUseCase(
    private val coupleRepository: CoupleRepository,
    private val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
    ): List<Anniversary> {
        val coupleId = coupleRepository.readCoupleId()
        return calendarRepository
            .getAnniversaryList(
                coupleId = coupleId,
                startDate = startDate,
                endDate = endDate,
            )
    }
}
