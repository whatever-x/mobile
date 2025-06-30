package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode

class GetCoupleInvitationCodeUseCase(
    private val coupleRepository: CoupleRepository,
) {
    suspend operator fun invoke(): CoupleInvitationCode = coupleRepository.getCoupleInvitationCode()
}
