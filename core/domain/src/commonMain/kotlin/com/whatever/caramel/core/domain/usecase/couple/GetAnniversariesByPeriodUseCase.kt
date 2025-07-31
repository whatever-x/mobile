package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.model.couple.AnniversariesOnDate

class GetAnniversariesByPeriodUseCase(
    private val coupleRepository: CoupleRepository,
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String,
    ): List<AnniversariesOnDate> {
        val coupleId = coupleRepository.getCoupleId()
        return coupleRepository
            .getAnniversaries(
                coupleId = coupleId,
                startDate = startDate,
                endDate = endDate,
            ).groupBy { it.date }
            .map { (date, anniversaries) -> AnniversariesOnDate(date, anniversaries) }
    }
}
