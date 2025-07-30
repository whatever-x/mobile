package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.params.content.ContentParameterType

// 한 유즈케이스에서 2가지 기능을 담당하고 있어 분리 필요
class CreateContentUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val contentRepository: ContentRepository,
) {
    suspend operator fun invoke(parameter: ContentParameterType) =
        when (parameter) {
            is ContentParameterType.Calendar -> {
                scheduleRepository.createSchedule(parameter.param)
            }

            is ContentParameterType.Memo -> {
                contentRepository.createMemo(parameter.param)
            }
        }
}
