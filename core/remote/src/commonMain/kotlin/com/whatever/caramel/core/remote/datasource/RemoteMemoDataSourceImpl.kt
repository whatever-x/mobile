package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.content.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.content.response.CreateMemoResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteMemoDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteMemoDataSource {
    override suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse =
        authClient.post(CREATE_CONTENT_URL) {
            setBody(body = request)
        }.getBody()

    companion object {
        private const val CREATE_CONTENT_URL = "/v1/content/memo"
    }
} 