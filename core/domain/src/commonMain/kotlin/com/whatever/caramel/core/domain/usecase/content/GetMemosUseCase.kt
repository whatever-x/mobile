package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor

class GetMemosUseCase(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(
        size : Int? = null,
        cursor : String? = null,
        sortType : String? = null,
        tagId : Long? = null
    ): MemoWithCursor {
        val requestTagId = if(tagId == 0L) null else tagId
        return memoRepository.getMemos(
            size = size,
            cursor = cursor,
            sortType = sortType,
            tagId = requestTagId
        )
    }
}