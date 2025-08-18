package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.app.PlatformDTO
import com.whatever.caramel.core.remote.dto.app.response.GetUpdatePolicyResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.koin.core.annotation.Named

class RemoteAppDataSourceImpl(
    @Named("DefaultClient") private val defaultClient: HttpClient,
) : RemoteAppDataSource {
    override suspend fun fetchRequirementUpdate(
        platform: PlatformDTO,
        versionCode: Int,
    ): GetUpdatePolicyResponse =
        defaultClient
            .get("$BASE_APP_URL/update-policy") {
                header(key = "Os-Type", value = platform)
                parameter(key = "currentVersionCode", value = versionCode)
            }.getBody()

    companion object {
        private const val BASE_APP_URL = "/v1/client-versions"
    }
}
