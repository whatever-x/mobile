package com.whatever.caramel.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface LocalAppDataSource {
    suspend fun saveAppLaunchCount(count: Int)
    suspend fun fetchAppLaunchCount(): Int

    suspend fun saveBalanceGameParticipationCount(count: Int)
    suspend fun fetchBalanceGameParticipationCount(): Flow<Int>

    suspend fun saveMemoCreateCount(count : Int)
    suspend fun fetchMemoCreateCount(): Flow<Int>

    suspend fun saveScheduleCreateCount(count: Int)
    suspend fun fetchScheduleCreateCount(): Flow<Int>

    suspend fun saveInAppReviewRequestDate(dateTime : String)
    suspend fun fetchInAppReviewRequestDate() : String
}
