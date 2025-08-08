package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.tag.TagDataResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.koin.core.annotation.Named

internal class RemoteTagDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteTagDataSource {
    override suspend fun fetchTagList(): TagDataResponse = authClient.get(TAG_BASE_URL).getBody()

    companion object {
        private const val TAG_BASE_URL = "/v1/tags"
    }
}
