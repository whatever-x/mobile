package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.repository.ContentRepository

class GetTagUseCase(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(): List<Tag> = contentRepository.getTagList()
}