package com.whatever.caramel.core.domain.params.content

import com.whatever.caramel.core.domain.params.content.memo.MemoParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter

sealed class ContentParameterType {
    data class Calendar(
        val param: ScheduleParameter,
    ) : ContentParameterType()

    data class Memo(
        val param: MemoParameter,
    ) : ContentParameterType()
}