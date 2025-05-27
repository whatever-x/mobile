package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.content.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.content.response.CreateMemoResponse

interface RemoteMemoDataSource {
    suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse
} 