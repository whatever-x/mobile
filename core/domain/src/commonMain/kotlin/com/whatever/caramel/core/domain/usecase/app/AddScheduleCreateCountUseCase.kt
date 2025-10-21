package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class AddScheduleCreateCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        val savedValue = appRepository.getScheduleCreateCount()
        appRepository.setScheduleCreateCount(savedValue + 1)
    }
}