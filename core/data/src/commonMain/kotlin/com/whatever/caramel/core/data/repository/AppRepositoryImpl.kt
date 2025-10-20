package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCheckForceUpdateResult
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.LocalAppDataSource
import com.whatever.caramel.core.domain.repository.AppRepository
import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.remote.datasource.RemoteAppDataSource
import com.whatever.caramel.core.remote.dto.app.PlatformDto

class AppRepositoryImpl(
    private val localAppDataSource: LocalAppDataSource,
    private val remoteAppDataSource: RemoteAppDataSource,
) : AppRepository {
    override suspend fun getMinVersion(
        appPlatform: Platform.AppPlatform,
        versionCode: Int,
    ): CheckForceUpdateResult =
        safeCall {
            remoteAppDataSource
                .fetchRequirementUpdate(
                    platform = PlatformDto.valueOf(appPlatform.name),
                    versionCode = versionCode,
                ).toCheckForceUpdateResult()
        }

    override suspend fun getAppLaunchCount(): Int = localAppDataSource.fetchAppLaunchCount()

    override suspend fun setAppLaunchCount(count: Int) {
        localAppDataSource.saveAppLaunchCount(count)
    }
}
