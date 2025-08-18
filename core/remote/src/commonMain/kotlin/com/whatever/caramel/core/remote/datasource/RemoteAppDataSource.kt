package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.app.PlatformDTO
import com.whatever.caramel.core.remote.dto.app.response.GetUpdatePolicyResponse

interface RemoteAppDataSource {
    suspend fun fetchRequirementUpdate(
        platform: PlatformDTO,
        versionCode: Int,
    ): GetUpdatePolicyResponse
}
