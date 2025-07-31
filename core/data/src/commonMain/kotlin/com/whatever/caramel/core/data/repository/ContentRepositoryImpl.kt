package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toMemo
import com.whatever.caramel.core.data.mapper.toMemoMetaData
import com.whatever.caramel.core.data.mapper.toMemosWithCursor
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.params.content.MemoEditParameter
import com.whatever.caramel.core.domain.model.content.ContentMetadata
import com.whatever.caramel.core.domain.params.content.MemoParameter
import com.whatever.caramel.core.domain.model.content.ContentWithCursor
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSource
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto
import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.DateTimeInfoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.tag.TagRequest

class ContentRepositoryImpl(
    private val remoteMemoDataSource: RemoteMemoDataSource,
) : ContentRepository {
    override suspend fun createMemo(parameter: MemoParameter): ContentMetadata {
        val request =
            CreateMemoRequest(
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                tags = parameter.tags?.map { TagRequest(it) },
                contentAssignee = ContentAssigneeDto.valueOf(value = parameter.contentAssignee.name),
            )
        return safeCall {
            remoteMemoDataSource.createMemo(request).toMemoMetaData()
        }
    }

    override suspend fun updateMemo(
        memoId: Long,
        parameter: MemoEditParameter,
    ) {
        val request =
            UpdateMemoRequest(
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                tagList = parameter.tagIds?.map { TagRequest(it) },
                dateTimeInfo =
                    parameter.scheduleDateTime?.run {
                        DateTimeInfoRequest(
                            startDateTime = startDateTime,
                            startTimezone = startTimezone,
                            endDateTime = endDateTime,
                            endTimezone = endTimezone,
                        )
                    },
                contentAssignee = ContentAssigneeDto.valueOf(value = parameter.contentAssignee.name),
            )
        safeCall {
            remoteMemoDataSource.updateMemo(memoId, request)
        }
    }

    override suspend fun deleteMemo(memoId: Long) {
        safeCall {
            remoteMemoDataSource.deleteMemo(memoId)
        }
    }

    override suspend fun getMemos(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?,
    ): ContentWithCursor =
        safeCall {
            remoteMemoDataSource.getMemos(size, cursor, sortType, tagId).toMemosWithCursor()
        }

    override suspend fun getMemo(memoId: Long): Memo =
        safeCall {
            val response = remoteMemoDataSource.getMemo(memoId)
            response.toMemo()
        }
}
