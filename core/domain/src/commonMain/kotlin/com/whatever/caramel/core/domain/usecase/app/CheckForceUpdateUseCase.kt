package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository
import com.whatever.caramel.core.domain.vo.app.Platform

class CheckForceUpdateUseCase(
    private val appRepository: AppRepository,
    private val platform: Platform,
) {
    suspend operator fun invoke(): Boolean =
        appRepository.getUpdateRequirement(
            appPlatform = platform.appPlatform,
            versionCode = platform.versionCode
        )
}