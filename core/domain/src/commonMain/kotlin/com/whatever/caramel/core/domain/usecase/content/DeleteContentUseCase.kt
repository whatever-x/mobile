package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.repository.ContentRepository

class DeleteContentUseCase(
    private val contentRepository: ContentRepository,
) {
    suspend operator fun invoke(memoId: Long) {
        contentRepository.deleteMemo(memoId)
    }
}
