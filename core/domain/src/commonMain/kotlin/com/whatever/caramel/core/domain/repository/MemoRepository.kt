package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.memo.MemoDetail
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata
import com.whatever.caramel.core.domain.vo.memo.MemoParameter

interface MemoRepository {
    suspend fun createMemo(parameter: MemoParameter): MemoMetadata
    suspend fun updateMemo(memoId: Long, parameter: MemoEditParameter)
    suspend fun deleteMemo(memoId: Long)
    suspend fun getMemoDetail(memoId: Long): MemoDetail
} 