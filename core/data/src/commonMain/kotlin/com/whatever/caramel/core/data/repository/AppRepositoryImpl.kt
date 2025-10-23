package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCheckForceUpdateResult
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.LocalAppDataSource
import com.whatever.caramel.core.domain.repository.AppRepository
import com.whatever.caramel.core.domain.vo.app.CheckForceUpdateResult
import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.remote.datasource.RemoteAppDataSource
import com.whatever.caramel.core.remote.dto.app.PlatformDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDateTime

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

    override suspend fun getAppLaunchCount(): Int = localAppDataSource.appLaunchCountFlow.first()

    override suspend fun setAppLaunchCount(count: Int) {
        localAppDataSource.saveAppLaunchCount(count)
    }

    override suspend fun setActivityParticipationCount(count: Int) {
        localAppDataSource.saveAppLaunchCount(count)
    }

    override suspend fun getActivityParticipationCount(): Int = localAppDataSource.activityParticipationCountFlow.first()

    override suspend fun setInAppReviewRequestDate(dateTime: LocalDateTime) =
        localAppDataSource.saveInAppReviewRequestDate(dateTime = dateTime.toString())

    override suspend fun getInAppReviewRequestDate(): LocalDateTime =
        runCatching {
            val dateString = localAppDataSource.fetchInAppReviewRequestDate()
            LocalDateTime.parse(dateString)
        }.getOrElse { LocalDateTime(1970, 1, 1, 0, 0) }

    override suspend fun getAppActivityFlow(): Flow<Pair<Int, Int>> =
        combine(
            localAppDataSource.appLaunchCountFlow,
            localAppDataSource.activityParticipationCountFlow,
        ) { appLaunchCount, activityParticipationCount ->
            Pair(appLaunchCount, activityParticipationCount)
        }.distinctUntilChanged()
}
