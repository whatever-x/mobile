package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface CalendarSideEffect : UiSideEffect {
    data class NavigateToTodoDetail(val id: Long, val contentType: ContentType) : CalendarSideEffect
    data class OpenWebView(val url: String) : CalendarSideEffect
    data class NavigateToAddSchedule(val dateTimeString: String) : CalendarSideEffect
}