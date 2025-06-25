package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toTags
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.repository.TagRepository
import com.whatever.caramel.core.remote.datasource.RemoteTagDataSource

class TagRepositoryImpl(
    private val remoteTagDataSource: RemoteTagDataSource
) : TagRepository {
    override suspend fun getTags(): List<Tag> {
        return safeCall {
            remoteTagDataSource.getTags().tags.toTags()
        }
    }
} 