package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.params.content.MemoEditParameter

class UpdateContentUseCase(
    private val contentRepository: ContentRepository,
) {
    suspend operator fun invoke(
        memoId: Long,
        parameter: MemoEditParameter,
    ) {
        contentRepository.updateMemo(memoId, parameter)
    }
}
