package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.CursoredContentResponse
import com.whatever.caramel.core.remote.dto.memo.response.MemoResponse

interface RemoteMemoDataSource {
    suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse

    suspend fun updateMemo(
        memoId: Long,
        updateMemoRequest: UpdateMemoRequest,
    )

    suspend fun deleteMemo(memoId: Long)

    suspend fun fetchMemo(memoId: Long): MemoResponse

    suspend fun fetchMemoList(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?,
    ): CursoredContentResponse
}
