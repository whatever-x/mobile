package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.params.content.ContentParameterType
import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.usecase.app.AddActivityParticipationCountUseCase

class CreateContentUseCase(
    private val scheduleRepository: ScheduleRepository,
    private val memoRepository: MemoRepository,
    private val addActivityParticipationCountUseCase: AddActivityParticipationCountUseCase,
) {
    suspend operator fun invoke(parameter: ContentParameterType) {
        when (parameter) {
            is ContentParameterType.Calendar -> {
                scheduleRepository.createSchedule(parameter.param)
            }

            is ContentParameterType.Memo -> {
                memoRepository.createMemo(parameter.param)
            }
        }
        addActivityParticipationCountUseCase()
    }
}
