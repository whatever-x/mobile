package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.policy.AppPolicy
import com.whatever.caramel.core.domain.repository.AppRepository
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class CheckReviewRequestAvailableUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke(currentDateTime: LocalDateTime): Boolean {
        val lastReviewRequestDateInstant =
            appRepository.getReviewRequestDate().toInstant(TimeZone.currentSystemDefault())
        val currentDateTimeInstant = currentDateTime.toInstant(TimeZone.currentSystemDefault())
        val durationSinceLastReviewRequest = currentDateTimeInstant - lastReviewRequestDateInstant
        val timeAvailable = durationSinceLastReviewRequest.inWholeDays >= AppPolicy.MIN_REVIEW_REQUEST_INTERVAL_DAY

        val appLaunchCount = appRepository.getAppLaunchCount()
        val available = appLaunchCount >= AppPolicy.MIN_APP_LAUNCH_COUNT_FOR_REVIEW &&
                timeAvailable

        if (available) appRepository.setReviewRequestDate(currentDateTime)

        return available
    }
}