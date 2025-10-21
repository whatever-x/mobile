package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class AddBalanceGameParticipationCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        val savedValue = appRepository.getBalanceGameParticipationCount()
        appRepository.setBalanceGameParticipationCount(savedValue + 1)
    }
}