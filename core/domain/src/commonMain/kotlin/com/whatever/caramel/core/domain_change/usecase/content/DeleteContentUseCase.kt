package com.whatever.caramel.core.domain_change.usecase.content

import com.whatever.caramel.core.domain.repository.ContentRepository

class DeleteContentUseCase(
    private val contentRepository: ContentRepository,
) {
    suspend operator fun invoke(memoId: Long) {
        contentRepository.deleteMemo(memoId)
    }
}
