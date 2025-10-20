package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class AddAppLaunchCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() {
        val launchCount = appRepository.getAppLaunchCount()
        appRepository.setAppLaunchCount(launchCount + 1)
    }
}