package com.whatever.caramel.core.domain.usecase.tag

import com.whatever.caramel.core.domain.entity.Tag

class GetTagUseCase(
    private val tagRepository: TagRepository,
) {
    suspend operator fun invoke(): List<Tag> = tagRepository.getTags()
}
