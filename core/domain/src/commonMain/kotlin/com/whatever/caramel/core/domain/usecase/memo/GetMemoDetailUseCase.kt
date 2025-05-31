package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.memo.MemoDetail

class GetMemoDetailUseCase(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(memoId: Long): MemoDetail {
        return memoRepository.getMemoDetail(memoId)
    }
} 