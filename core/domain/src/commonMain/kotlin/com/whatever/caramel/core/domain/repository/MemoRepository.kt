package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor

interface MemoRepository {
    suspend fun createMemo(parameter: MemoParameter): MemoMetadata
    suspend fun getMemos(
        size : Int?,
        cursor : String?,
        sortType : String?,
        tagId : Long?
    ) : MemoWithCursor
} 