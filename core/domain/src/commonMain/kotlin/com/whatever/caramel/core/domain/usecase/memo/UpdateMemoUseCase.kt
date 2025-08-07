package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.params.content.memo.MemoEditParameter
import com.whatever.caramel.core.domain.repository.MemoRepository

class UpdateMemoUseCase(
    private val memoRepository: MemoRepository,
) {
    suspend operator fun invoke(
        memoId: Long,
        parameter: MemoEditParameter,
    ) {
        memoRepository.updateMemo(memoId, parameter)
    }
}
