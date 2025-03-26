package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository

class GetCoupleInvitationCodeUseCase(
    private val coupleRepository: CoupleRepository
) {
    suspend operator fun invoke() : String {
        return coupleRepository.generateCoupleInvitationCode()
    }
}