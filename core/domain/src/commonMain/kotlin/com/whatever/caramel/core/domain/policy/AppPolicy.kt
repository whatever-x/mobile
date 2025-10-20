package com.whatever.caramel.core.domain.policy

data object AppPolicy {
    const val MIN_APP_LAUNCH_COUNT_FOR_REVIEW = 3
    const val MIN_REVIEW_REQUEST_INTERVAL_DAY = 120
}