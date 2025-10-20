package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform
import kotlinx.datetime.LocalDateTime

interface AppRepository {
    suspend fun getMinVersion(
        appPlatform: Platform.AppPlatform,
        versionCode: Int,
    ): CheckForceUpdateResult

    suspend fun getReviewRequestDate(): LocalDateTime
    suspend fun setReviewRequestDate(date: LocalDateTime)
    suspend fun getAppLaunchCount(): Int
    suspend fun setAppLaunchCount(count : Int)
}
