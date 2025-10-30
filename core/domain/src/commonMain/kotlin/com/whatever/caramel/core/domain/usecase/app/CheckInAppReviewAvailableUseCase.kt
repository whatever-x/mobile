package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.event.AnalyticsEventBus
import com.whatever.caramel.core.domain.event.AnalyticsLogEvent
import com.whatever.caramel.core.domain.policy.AppPolicy
import com.whatever.caramel.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class CheckInAppReviewAvailableUseCase(
    private val appRepository: AppRepository,
    private val analyticsEventBus: AnalyticsEventBus,
) {
    suspend operator fun invoke(): Flow<Boolean> {
        val appActivityFlow = appRepository.getAppActivityFlow()

        return appActivityFlow
            .map { (appLaunchCount, activityParticipationCount) ->
                val appActivityAvailable =
                    activityParticipationCount >= AppPolicy.APP_ACTIVITY_MIN_COUNT_FOR_REVIEW
                val appLaunchCountAvailable =
                    appLaunchCount >= AppPolicy.APP_LAUNCH_MIN_COUNT_FOR_REVIEW
                val now =
                    Clock.System
                        .now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())

                val available =
                    appLaunchCountAvailable &&
                        appActivityAvailable &&
                        checkInAppReviewRequestDate(
                            now = now,
                        )
                if (available) {
                    appRepository.setInAppReviewRequestDate(dateTime = now)
                }
                available
            }.distinctUntilChanged()
            .onEach { available ->
                if (available) {
                    analyticsEventBus.emit(event = AnalyticsLogEvent.RequestedInAppReview)
                }
            }
    }

    private suspend fun checkInAppReviewRequestDate(now: LocalDateTime): Boolean {
        val nowInstant = now.toInstant(TimeZone.currentSystemDefault())

        val lastInAppReviewRequestDate =
            appRepository
                .getInAppReviewRequestDate()
                .toInstant(TimeZone.currentSystemDefault())

        return (nowInstant - lastInAppReviewRequestDate).inWholeDays > AppPolicy.APP_IN_APP_REVIEW_REQUEST_MIN_DAY
    }
}
