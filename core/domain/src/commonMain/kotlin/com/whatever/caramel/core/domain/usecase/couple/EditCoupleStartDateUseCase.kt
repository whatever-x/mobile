package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository

class EditCoupleStartDateUseCase(
    private val coupleRepository: CoupleRepository,
) {
    suspend operator fun invoke(startDate: String) {
        val coupleId = coupleRepository.readCoupleId()
        coupleRepository.updateCoupleStartDate(coupleId, startDate)
    }
}
