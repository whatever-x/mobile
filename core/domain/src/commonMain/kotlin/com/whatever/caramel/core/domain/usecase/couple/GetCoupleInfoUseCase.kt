package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.repository.CoupleRepository

class GetCoupleInfoUseCase(
    private val coupleRepository: CoupleRepository
) {

    suspend operator fun invoke(): Couple {
        val coupleInfo = coupleRepository.getCoupleInfo()

        return coupleInfo
    }

}