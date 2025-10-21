package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class AddMemoCreateCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        val savedValue = appRepository.getMemoCreateCount()
        appRepository.setMemoCreateCount(savedValue + 1)
    }
}