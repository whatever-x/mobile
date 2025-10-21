package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface AppRepository {
    suspend fun getMinVersion(
        appPlatform: Platform.AppPlatform,
        versionCode: Int,
    ): CheckForceUpdateResult

    suspend fun getAppLaunchCount(): Int
    suspend fun setAppLaunchCount(count: Int)

    suspend fun setBalanceGameParticipationCount(count: Int)
    suspend fun setContentCreateCount(count: Int)
    suspend fun getBalanceGameParticipationCount(): Int
    suspend fun getContentCreateCount(): Int
    suspend fun setInAppReviewRequestDate(dateTime : LocalDateTime)
    suspend fun getInAppReviewRequestDate() : LocalDateTime
    suspend fun getAppActivityFlow(): Flow<Pair<Int, Int>>
}
