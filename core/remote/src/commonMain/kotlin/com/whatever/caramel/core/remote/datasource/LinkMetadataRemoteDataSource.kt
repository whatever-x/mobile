package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.common.OgTagDto

interface LinkMetadataRemoteDataSource {
    suspend fun fetchLinkMetadata(url: String): OgTagDto?
}
