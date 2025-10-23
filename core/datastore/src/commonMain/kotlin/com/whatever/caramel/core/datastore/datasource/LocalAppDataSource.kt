package com.whatever.caramel.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface LocalAppDataSource {
    val activityParticipationCountFlow : Flow<Int>
    val appLaunchCountFlow : Flow<Int>

    suspend fun saveActivityParticipationCount(count: Int)

    suspend fun saveAppLaunchCount(count: Int)

    suspend fun saveInAppReviewRequestDate(dateTime: String)

    suspend fun fetchInAppReviewRequestDate(): String
}
