package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class GetAppLaunchCountUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() : Int = appRepository.getAppLaunchCount()
}