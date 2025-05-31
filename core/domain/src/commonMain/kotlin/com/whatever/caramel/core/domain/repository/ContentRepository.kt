package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter

interface ContentRepository {
    suspend fun createMemo(parameter: MemoParameter): MemoMetadata
    suspend fun updateMemo(memoId: Long, parameter: MemoEditParameter)
    suspend fun deleteMemo(memoId: Long)
} 