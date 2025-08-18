package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform

interface AppRepository {
    suspend fun getUpdateRequirement(
        appPlatform: Platform.AppPlatform,
        versionCode: Int,
    ): CheckForceUpdateResult
}
