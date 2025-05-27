package com.whatever.caramel.core.domain.usecase.tag

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.repository.TagRepository

class GetTagUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(): List<Tag> {
        return tagRepository.getTags()
    }
}