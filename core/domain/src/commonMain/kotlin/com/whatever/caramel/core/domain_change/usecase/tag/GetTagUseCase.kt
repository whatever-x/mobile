package com.whatever.caramel.core.domain_change.usecase.tag

class GetTagUseCase(
    private val tagRepository: TagRepository,
) {
    suspend operator fun invoke(): List<Tag> = tagRepository.getTags()
}
