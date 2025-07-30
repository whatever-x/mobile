package com.whatever.caramel.core.domain.vo.content

import com.whatever.caramel.core.domain.vo.content.memo.CreateMemoParameter
import com.whatever.caramel.core.domain.vo.content.schedule.CreateScheduleParameter

sealed class CreateContentParameterType {
    data class Schedule(
        val param: CreateScheduleParameter,
    ) : CreateContentParameterType()

    data class Memo(
        val param: CreateMemoParameter,
    ) : CreateContentParameterType()
}
