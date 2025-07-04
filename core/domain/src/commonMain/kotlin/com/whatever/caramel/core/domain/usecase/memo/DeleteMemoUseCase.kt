package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.MemoRepository

class DeleteMemoUseCase(
    private val memoRepository: MemoRepository,
) {
    suspend operator fun invoke(memoId: Long) {
        memoRepository.deleteMemo(memoId)
    }
}
