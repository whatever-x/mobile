package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter
import com.whatever.caramel.core.domain.vo.memo.MemoParameter

class UpdateMemoUseCase(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(memoId: Long, parameter: MemoEditParameter) {
        contentRepository.updateMemo(memoId, parameter)
    }
} 