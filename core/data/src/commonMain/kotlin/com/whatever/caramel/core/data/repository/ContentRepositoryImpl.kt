package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toContentMetaData
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.remote.datasource.RemoteContentDataSource
import com.whatever.caramel.core.remote.dto.content.request.CreateContentRequest

class ContentRepositoryImpl(
    private val remoteContentDataSource: RemoteContentDataSource
) : ContentRepository {
    override suspend fun createContent(parameter: MemoParameter): MemoMetadata {
        val request = CreateContentRequest(
            title = parameter.title,
            description = parameter.description,
            isCompleted = parameter.isCompleted,
            tags = parameter.tags
        )
        return safeCall {
            remoteContentDataSource.createContent(request).toContentMetaData()
        }
    }
} 