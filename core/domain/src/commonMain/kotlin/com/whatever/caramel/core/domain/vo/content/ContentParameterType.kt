package com.whatever.caramel.core.domain.vo.content

import com.whatever.caramel.core.domain.vo.calendar.ScheduleParameter
import com.whatever.caramel.core.domain.vo.memo.MemoParameter

sealed class ContentParameterType {
    data class Calendar(val param: ScheduleParameter) : ContentParameterType()
    data class Memo(val param: MemoParameter) : ContentParameterType()
} 