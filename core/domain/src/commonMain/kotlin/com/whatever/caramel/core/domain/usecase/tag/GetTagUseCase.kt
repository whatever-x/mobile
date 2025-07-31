package com.whatever.caramel.core.domain.usecase.tag

class GetTagUseCase(
    private val tagRepository: TagRepository,
) {
    suspend operator fun invoke(): List<Tag> = tagRepository.getTags()
}
