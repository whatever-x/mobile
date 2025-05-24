package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship

class GetCoupleRelationshipInfoUseCase(
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke(): CoupleRelationship {
        val savedCoupleId = coupleRepository.getCoupleId()
        return coupleRepository.getCoupleInfo(savedCoupleId)
    }
}