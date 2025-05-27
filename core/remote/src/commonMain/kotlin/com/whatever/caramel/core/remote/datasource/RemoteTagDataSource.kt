package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.tag.TagDataResponse

interface RemoteTagDataSource {
    suspend fun getTags(): TagDataResponse
} 