package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCheckForceUpdateResult
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.AppRepository
import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.remote.datasource.RemoteAppDataSource
import com.whatever.caramel.core.remote.dto.app.PlatformDTO

class AppRepositoryImpl(
    private val remoteAppDataSource: RemoteAppDataSource,
) : AppRepository {
    override suspend fun getUpdateRequirement(
        appPlatform: Platform.AppPlatform,
        versionCode: Int,
    ): CheckForceUpdateResult =
        safeCall {
            remoteAppDataSource
                .fetchRequirementUpdate(
                    platform = PlatformDTO.valueOf(appPlatform.name),
                    versionCode = versionCode,
                ).toCheckForceUpdateResult()
        }
}
