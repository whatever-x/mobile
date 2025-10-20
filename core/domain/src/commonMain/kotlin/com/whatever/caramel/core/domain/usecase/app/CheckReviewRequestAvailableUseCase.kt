package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.policy.AppPolicy
import com.whatever.caramel.core.domain.repository.AppRepository
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class CheckReviewRequestAvailableUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke(): Boolean {
        val appLaunchCount = appRepository.getAppLaunchCount()
        return appLaunchCount >= AppPolicy.APP_LAUNCH_MIN_COUNT_FOR_REVIEW
    }
}
