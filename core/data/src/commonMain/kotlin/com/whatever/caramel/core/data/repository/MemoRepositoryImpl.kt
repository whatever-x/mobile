package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toMemo
import com.whatever.caramel.core.data.mapper.toMemosWithCursor
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.content.memo.CreateMemoParameter
import com.whatever.caramel.core.domain.vo.content.memo.EditMemoParameter
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSource
import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto
import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.DateTimeInfoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.tag.TagRequest

class MemoRepositoryImpl(
    private val remoteMemoDataSource: RemoteMemoDataSource,
) : MemoRepository {
    override suspend fun createMemo(parameter: CreateMemoParameter) {
        val request =
            CreateMemoRequest(
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                tags = parameter.tags?.map { TagRequest(it) },
                contentAssignee = ContentAssigneeDto.valueOf(value = parameter.contentAssignee.name),
            )
        return safeCall {
            remoteMemoDataSource.createMemo(request)
        }
    }

    override suspend fun updateMemo(
        memoId: Long,
        parameter: EditMemoParameter,
    ) {
        val request =
            UpdateMemoRequest(
                title = parameter.title,
                description = parameter.description,
                isCompleted = parameter.isCompleted,
                tagList = parameter.tagIds?.map { TagRequest(it) },
                dateTimeInfo =
                    parameter.dateTimeInfo?.run {
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
    ): MemoWithCursor =
        safeCall {
            remoteMemoDataSource.getMemos(size, cursor, sortType, tagId).toMemosWithCursor()
        }

    override suspend fun getMemo(memoId: Long): Memo =
        safeCall {
            val response = remoteMemoDataSource.getMemo(memoId)
            response.toMemo()
        }
}
