package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.content.request.CreateContentRequest
import com.whatever.caramel.core.remote.dto.content.response.CreateContentResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteContentDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient, // Assuming AuthClient is needed
) : RemoteContentDataSource {
    override suspend fun createContent(request: CreateContentRequest): CreateContentResponse =
        authClient.post(CREATE_CONTENT_URL) {
            setBody(body = request)
        }.getBody()

    companion object {
        private const val CREATE_CONTENT_URL = "/v1/content" // Please verify this endpoint
    }
} 