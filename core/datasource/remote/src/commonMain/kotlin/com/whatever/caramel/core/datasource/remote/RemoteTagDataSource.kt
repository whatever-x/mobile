package com.whatever.caramel.core.datasource.remote

import com.whatever.caramel.core.datasource.remote.dto.tag.TagDataResponse

interface RemoteTagDataSource {
    suspend fun fetchTagList(): TagDataResponse
}
