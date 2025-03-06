package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface CalendarIntent : UiIntent {

    data object ClickCreateTodoButton : CalendarIntent

    data object ClickTodoCard : CalendarIntent

}