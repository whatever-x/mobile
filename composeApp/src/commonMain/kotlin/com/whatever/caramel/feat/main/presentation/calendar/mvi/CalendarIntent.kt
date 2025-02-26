package com.whatever.caramel.feat.main.presentation.calendar.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface CalendarIntent : UiIntent {

    data object ClickCreateTodoButton : CalendarIntent

    data object ClickTodoCard : CalendarIntent

}