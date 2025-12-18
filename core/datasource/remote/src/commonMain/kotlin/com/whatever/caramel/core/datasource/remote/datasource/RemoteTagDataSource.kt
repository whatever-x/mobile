package com.whatever.caramel.core.datasource.remote.datasource

import com.whatever.caramel.core.datasource.remote.dto.tag.TagDataResponse

interface RemoteTagDataSource {
    suspend fun fetchTagList(): TagDataResponse
}
