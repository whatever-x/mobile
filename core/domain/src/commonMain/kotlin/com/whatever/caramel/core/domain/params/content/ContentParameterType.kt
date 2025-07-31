package com.whatever.caramel.core.domain.params.content

import com.whatever.caramel.core.domain.params.schedule.ScheduleParameter

/**
 * UseCase에서 많은 파라미터를 가질때 필요한 파라미터들
 * */
sealed class ContentParameterType {
    data class Calendar(
        val param: ScheduleParameter,
    ) : ContentParameterType()

    data class Memo(
        val param: MemoParameter,
    ) : ContentParameterType()
}