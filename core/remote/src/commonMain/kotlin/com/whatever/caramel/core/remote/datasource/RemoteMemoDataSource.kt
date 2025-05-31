package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.content.request.CreateMemoRequest
import com.whatever.caramel.core.remote.dto.content.request.UpdateMemoRequest
import com.whatever.caramel.core.remote.dto.content.response.CreateMemoResponse
import com.whatever.caramel.core.remote.dto.content.response.CursoredContentResponse

interface RemoteMemoDataSource {
    suspend fun createMemo(request: CreateMemoRequest): CreateMemoResponse
    suspend fun updateMemo(memoId: Long, updateMemoRequest: UpdateMemoRequest)
    suspend fun deleteMemo(memoId: Long)
    suspend fun getMemos(
        size : Int?,
        cursor : String?,
        sortType : String?,
        tagId : Long?
    ) : CursoredContentResponse
}