package com.whatever.caramel.core.domain_change.usecase.content

import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.model.content.ContentWithCursor

class GetContentsUseCase(
    private val contentRepository: ContentRepository,
) {
    suspend operator fun invoke(
        size: Int? = null,
        cursor: String? = null,
        sortType: String? = null,
        tagId: Long? = null,
    ): ContentWithCursor =
        contentRepository.getMemos(
            size = size,
            cursor = cursor,
            sortType = sortType,
            tagId = tagId,
        )
}
