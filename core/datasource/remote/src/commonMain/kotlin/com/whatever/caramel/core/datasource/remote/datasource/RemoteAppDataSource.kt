package com.whatever.caramel.core.datasource.remote.datasource

import com.whatever.caramel.core.datasource.remote.dto.app.PlatformDto
import com.whatever.caramel.core.datasource.remote.dto.app.response.GetUpdatePolicyResponse

interface RemoteAppDataSource {
    suspend fun fetchRequirementUpdate(
        platform: PlatformDto,
        versionCode: Int,
    ): GetUpdatePolicyResponse
}
