package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.ContentRepository

class DeleteMemoUseCase(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(memoId: Long) {
        contentRepository.deleteMemo(memoId)
    }
} 