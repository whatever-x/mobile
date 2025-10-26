package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class IncrementActivityParticipationCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        val savedActivityParticipationCount = appRepository.getActivityParticipationCount()
        appRepository.setActivityParticipationCount(savedActivityParticipationCount + 1)
    }
}
