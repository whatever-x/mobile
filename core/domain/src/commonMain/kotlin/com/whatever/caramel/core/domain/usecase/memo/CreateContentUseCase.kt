package com.whatever.caramel.core.domain.usecase.memo

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.vo.item.ContentParameterType

class CreateContentUseCase(
    private val calendarRepository: CalendarRepository,
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(parameter: ContentParameterType): Long {
        return when (parameter) {
            is ContentParameterType.Calendar -> {
                calendarRepository.createSchedule(parameter.param).contentId
            }

            is ContentParameterType.Memo -> {
                memoRepository.createMemo(parameter.param).contentId
            }
        }
    }
}
