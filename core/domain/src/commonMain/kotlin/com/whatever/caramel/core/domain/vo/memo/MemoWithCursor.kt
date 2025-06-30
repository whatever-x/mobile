package com.whatever.caramel.core.domain.vo.memo

import com.whatever.caramel.core.domain.entity.Memo

data class MemoWithCursor(
    val nextCursor: String?,
    val memos: List<Memo>,
)
