package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toMemoMetaData
import com.whatever.caramel.core.data.mapper.toMemosWithCursor
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor
import com.whatever.caramel.core.remote.datasource.RemoteContentDataSource
import com.whatever.caramel.core.remote.dto.content.request.CreateMemoRequest

class ContentRepositoryImpl(
    private val remoteContentDataSource: RemoteContentDataSource
) : ContentRepository {
    override suspend fun createMemo(parameter: MemoParameter): MemoMetadata {
        val request = CreateMemoRequest(
            title = parameter.title,
            description = parameter.description,
            isCompleted = parameter.isCompleted,
            tags = parameter.tags
        )
        return safeCall {
            remoteContentDataSource.createMemo(request).toMemoMetaData()
        }
    }

    override suspend fun getMemos(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?
    ): MemoWithCursor {
        return safeCall {
            remoteContentDataSource.getMemos(size, cursor, sortType, tagId).toMemosWithCursor()
        }
    }
} 