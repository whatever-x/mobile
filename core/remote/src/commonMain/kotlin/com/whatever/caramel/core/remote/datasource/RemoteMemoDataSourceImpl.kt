package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.content.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.content.response.CreateMemoResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteMemoDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteMemoDataSource {
    override suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse =
        authClient.post(CONTENT_BASE_URL) {
            setBody(body = request)
        }.getBody()

    override suspend fun getMemos(
        pageSize: Int,
        cursor: String,
        sortType: String,
        tagId: Long,
    ): List<CreateMemoResponse> {
        return authClient.get(CONTENT_BASE_URL) {
            parameter("pageSize", pageSize)
            parameter("cursor", cursor)
            parameter("sortType", sortType)
            parameter("tagId", tagId)
        }.getBody()
    }

    companion object {
        private const val CONTENT_BASE_URL = "/v1/content/memo"
    }
} 