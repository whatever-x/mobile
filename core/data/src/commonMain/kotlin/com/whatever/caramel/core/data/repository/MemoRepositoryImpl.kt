package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toMemoMetaData
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSource
import com.whatever.caramel.core.remote.dto.content.request.CreateMemoRequest

class MemoRepositoryImpl(
    private val remoteMemoDataSource: RemoteMemoDataSource
) : ContentRepository {
    override suspend fun createMemo(parameter: MemoParameter): MemoMetadata {
        val request = CreateMemoRequest(
            title = parameter.title,
            description = parameter.description,
            isCompleted = parameter.isCompleted,
            tags = parameter.tags
        )
        return safeCall {
            remoteMemoDataSource.createMemo(request).toMemoMetaData()
        }
    }
} 