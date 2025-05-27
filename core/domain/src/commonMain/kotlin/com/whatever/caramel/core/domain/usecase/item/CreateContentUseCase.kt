package com.whatever.caramel.core.domain.usecase.item

import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.item.ContentParameterType

class CreateContentUseCase(
    private val calendarRepository: CalendarRepository,
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(parameter: ContentParameterType): Long {
        return when (parameter) {
            is ContentParameterType.Calendar -> {
                calendarRepository.createSchedule(parameter.param).contentId
            }

            is ContentParameterType.Memo -> {
                contentRepository.createMemo(parameter.param).contentId
            }
        }
    }
}
