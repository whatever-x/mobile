package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toMemo
import com.whatever.caramel.core.data.mapper.toMemoMetaData
import com.whatever.caramel.core.data.mapper.toMemosWithCursor
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSource
import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.DateTimeInfoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.tag.TagRequest

class MemoRepositoryImpl(
    private val remoteMemoDataSource: RemoteMemoDataSource
) : MemoRepository {
    override suspend fun createMemo(parameter: MemoParameter): MemoMetadata {
        val request = CreateMemoRequest(
            title = parameter.title,
            description = parameter.description,
            isCompleted = parameter.isCompleted,
            tags = parameter.tags?.map { TagRequest(it) }
        )
        return safeCall {
            remoteMemoDataSource.createMemo(request).toMemoMetaData()
        }
    }

    override suspend fun updateMemo(memoId: Long, parameter: MemoEditParameter) {
        val request = UpdateMemoRequest(
            title = parameter.title,
            description = parameter.description,
            isCompleted = parameter.isCompleted,
            tagList = parameter.tagIds?.map { TagRequest(it) },
            dateTimeInfo = parameter.dateTimeInfo?.run {
                DateTimeInfoRequest(
                    startDateTime = startDateTime,
                    startTimezone = startTimezone,
                    endDateTime = endDateTime,
                    endTimezone = endTimezone
                )
            }
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
        tagId: Long?
    ): MemoWithCursor {
        return safeCall {
            remoteMemoDataSource.getMemos(size, cursor, sortType, tagId).toMemosWithCursor()
        }
    }

    override suspend fun getMemo(memoId: Long): Memo {
        return safeCall {
            val response = remoteMemoDataSource.getMemo(memoId)
            response.toMemo()
        }
    }
}