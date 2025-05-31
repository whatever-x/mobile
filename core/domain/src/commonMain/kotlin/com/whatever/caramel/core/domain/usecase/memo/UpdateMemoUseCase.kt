package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter

class UpdateMemoUseCase(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(memoId: Long, parameter: MemoEditParameter) {
        contentRepository.updateMemo(memoId, parameter)
    }
} 