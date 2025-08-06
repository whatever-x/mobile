package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toLinkMetaData
import com.whatever.caramel.core.data.mapper.toTagList
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.content.LinkMetaData
import com.whatever.caramel.core.remote.datasource.LinkMetadataRemoteDataSource
import com.whatever.caramel.core.remote.datasource.RemoteTagDataSource

class ContentRepositoryImpl(
    private val remoteLinkMetadataRemoteDataSource: LinkMetadataRemoteDataSource,
    private val remoteTagDataSource: RemoteTagDataSource,
) : ContentRepository {
    override suspend fun getLinkMetadata(url: String): LinkMetaData? =
        remoteLinkMetadataRemoteDataSource.fetchLinkMetadata(url = url)?.toLinkMetaData()

    override suspend fun getTagList(): List<Tag> =
        safeCall {
            remoteTagDataSource.fetchTagList().tags.toTagList()
        }
}