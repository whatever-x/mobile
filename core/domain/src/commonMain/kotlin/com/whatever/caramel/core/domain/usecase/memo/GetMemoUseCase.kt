package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.repository.MemoRepository

class GetMemoUseCase(
    private val memoRepository: MemoRepository,
) {
    suspend operator fun invoke(memoId: Long): Memo {
        return memoRepository.getMemo(memoId)
    }
}
