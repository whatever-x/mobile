package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform

interface AppRepository {
    suspend fun getMinVersion(
        appPlatform: Platform.AppPlatform,
        versionCode: Int,
    ): CheckForceUpdateResult

    suspend fun getAppLaunchCount(): Int

    suspend fun setAppLaunchCount(count: Int)
}
