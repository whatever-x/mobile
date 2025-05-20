package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.domain.vo.memo.MemoMetadata

interface ContentRepository {
    suspend fun createContent(parameter: MemoParameter): MemoMetadata
} 