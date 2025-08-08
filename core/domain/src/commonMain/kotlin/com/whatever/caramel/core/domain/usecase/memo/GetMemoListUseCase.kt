package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.content.memo.MemoWithCursor

class GetMemoListUseCase(
    private val memoRepository: MemoRepository,
) {
    suspend operator fun invoke(
        size: Int? = null,
        cursor: String? = null,
        sortType: String? = null,
        tagId: Long? = null,
    ): MemoWithCursor =
        memoRepository.getMemoList(
            size = size,
            cursor = cursor,
            sortType = sortType,
            tagId = tagId,
        )
}
