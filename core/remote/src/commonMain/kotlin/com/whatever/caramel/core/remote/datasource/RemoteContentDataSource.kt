package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.content.request.CreateContentRequest
import com.whatever.caramel.core.remote.dto.content.response.CreateContentResponse

interface RemoteContentDataSource {
    suspend fun createContent(request: CreateContentRequest): CreateContentResponse
} 