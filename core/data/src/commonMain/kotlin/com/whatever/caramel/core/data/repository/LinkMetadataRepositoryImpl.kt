package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.domain.repository.LinkMetadataRepository
import com.whatever.caramel.core.domain.vo.common.LinkMetaData
import com.whatever.caramel.core.remote.datasource.LinkMetadataRemoteDataSource

class LinkMetadataRepositoryImpl(
    private val remoteDataSource: LinkMetadataRemoteDataSource
) : LinkMetadataRepository {
    override suspend fun getLinkMetadata(url: String): LinkMetaData? {
        return remoteDataSource.fetchLinkMetadata(url)?.let {
            LinkMetaData(
                url = it.url,
                title = it.title,
                imageUrl = it.image,
            )
        }
    }
} 