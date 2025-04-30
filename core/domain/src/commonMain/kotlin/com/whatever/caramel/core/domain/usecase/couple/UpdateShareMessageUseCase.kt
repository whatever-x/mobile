package com.whatever.caramel.core.domain.usecase.couple

import com.whatever.caramel.core.domain.repository.CoupleRepository

class UpdateShareMessageUseCase(
    private val coupleRepository: CoupleRepository,
) {
    suspend operator fun invoke(shareMessage: String): String {
        val coupleId = coupleRepository.getCoupleId()

        val coupleInfo = coupleRepository.updateShareMessage(
            coupleId = coupleId,
            shareMessage = shareMessage,
        )

        return coupleInfo.sharedMessage
    }
}