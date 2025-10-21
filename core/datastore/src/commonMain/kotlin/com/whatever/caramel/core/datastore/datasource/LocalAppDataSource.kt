package com.whatever.caramel.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface LocalAppDataSource {
    suspend fun saveAppLaunchCount(count: Int)
    suspend fun fetchAppLaunchCount(): Int

    suspend fun saveBalanceGameParticipationCount(count: Int)
    suspend fun fetchBalanceGameParticipationCount(): Flow<Int>

    suspend fun saveContentCreateCount(count: Int)
    suspend fun fetchContentCreateCount(): Flow<Int>

    suspend fun saveInAppReviewRequestDate(dateTime : String)
    suspend fun fetchInAppReviewRequestDate() : String
}
