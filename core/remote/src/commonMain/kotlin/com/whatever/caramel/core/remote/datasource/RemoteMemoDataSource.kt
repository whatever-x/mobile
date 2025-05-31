package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.memo.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.memo.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.memo.response.GetMemoResponse

interface RemoteMemoDataSource {
    suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse
    suspend fun updateMemo(memoId: Long, updateMemoRequest: UpdateMemoRequest)
    suspend fun deleteMemo(memoId: Long)
    suspend fun getMemoDetail(memoId: Long): GetMemoResponse
} 