package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.memo.MemoWithCursor

class GetMemosUseCase(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(
        size : Int? = null,
        cursor : String? = null,
        sortType : String? = null,
        tagId : Long? = null
    ): MemoWithCursor {
        return contentRepository.getMemos(
            size = size,
            cursor = cursor,
            sortType = sortType,
            tagId = tagId
        )
    }
}