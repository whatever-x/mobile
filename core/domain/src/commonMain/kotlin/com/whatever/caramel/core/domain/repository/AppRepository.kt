package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult

interface AppRepository {

    suspend fun getUpdateRequirement(
        appPlatform: Platform.AppPlatform,
        versionCode: Int
    ): CheckForceUpdateResult

}