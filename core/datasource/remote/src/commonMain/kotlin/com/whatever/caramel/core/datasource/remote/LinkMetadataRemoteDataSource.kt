package com.whatever.caramel.core.datasource.remote

import com.whatever.caramel.core.datasource.remote.dto.common.OgTagDto

interface LinkMetadataRemoteDataSource {
    suspend fun fetchLinkMetadata(url: String): OgTagDto?
}
