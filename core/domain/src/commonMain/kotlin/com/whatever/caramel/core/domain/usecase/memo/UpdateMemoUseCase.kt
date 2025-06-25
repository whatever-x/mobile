package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter

class UpdateMemoUseCase(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(memoId: Long, parameter: MemoEditParameter) {
        memoRepository.updateMemo(memoId, parameter)
    }
} 