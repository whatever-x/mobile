package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.tag.TagDataResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.koin.core.annotation.Named

internal class RemoteTagDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteTagDataSource {
    override suspend fun getTags(): TagDataResponse = authClient.get(GET_TAGS_URL).getBody()

    companion object {
        private const val GET_TAGS_URL = "v1/tags"
    }
}
