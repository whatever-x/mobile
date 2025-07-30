package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.vo.content.memo.EditMemoParameter
import com.whatever.caramel.core.domain.vo.content.memo.CreateMemoParameter
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor

interface MemoRepository {
    suspend fun createMemo(parameter: CreateMemoParameter)

    suspend fun getMemos(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?,
    ): MemoWithCursor

    suspend fun updateMemo(
        memoId: Long,
        parameter: EditMemoParameter,
    )

    suspend fun deleteMemo(memoId: Long)

    suspend fun getMemo(memoId: Long): Memo
}
