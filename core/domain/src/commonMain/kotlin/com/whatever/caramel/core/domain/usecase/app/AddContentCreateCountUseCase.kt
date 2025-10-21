package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class AddContentCreateCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        val savedValue = appRepository.getContentCreateCount()
        appRepository.setContentCreateCount(savedValue + 1)
    }
}
