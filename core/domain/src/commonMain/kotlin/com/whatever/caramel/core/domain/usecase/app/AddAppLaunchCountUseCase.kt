package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository

class AddAppLaunchCountUseCase(
    private val appRepository: AppRepository,
    private val coupleRepository: CoupleRepository,
) {
    suspend operator fun invoke() {
        if (coupleRepository.readCoupleId() == 0L) return

        val launchCount = appRepository.getAppLaunchCount()
        appRepository.setAppLaunchCount(launchCount + 1)
    }
}
