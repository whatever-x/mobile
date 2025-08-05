package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCreateMemoRequest
import com.whatever.caramel.core.data.mapper.toMemo
import com.whatever.caramel.core.data.mapper.toMemosWithCursor
import com.whatever.caramel.core.data.mapper.toUpdateMemoRequest
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.params.content.memo.MemoEditParameter
import com.whatever.caramel.core.domain.params.content.memo.MemoParameter
import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSource

class MemoRepositoryImpl(
    private val remoteMemoDataSource: RemoteMemoDataSource,
) : MemoRepository {
    override suspend fun createMemo(parameter: MemoParameter) {
        return safeCall {
            remoteMemoDataSource.createMemo(request = parameter.toCreateMemoRequest())
        }
    }

    override suspend fun updateMemo(
        memoId: Long,
        parameter: MemoEditParameter,
    ) {
        safeCall {
            remoteMemoDataSource.updateMemo(
                memoId = memoId,
                updateMemoRequest = parameter.toUpdateMemoRequest())
        }
    }

    override suspend fun deleteMemo(memoId: Long) {
        safeCall {
            remoteMemoDataSource.deleteMemo(memoId)
        }
    }

    override suspend fun getMemoList(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?,
    ): MemoWithCursor =
        safeCall {
            remoteMemoDataSource.fetchMemoList(size, cursor, sortType, tagId).toMemosWithCursor()
        }

    override suspend fun getMemo(memoId: Long): Memo =
        safeCall {
            val response = remoteMemoDataSource.fetchMemo(memoId)
            response.toMemo()
        }
}
