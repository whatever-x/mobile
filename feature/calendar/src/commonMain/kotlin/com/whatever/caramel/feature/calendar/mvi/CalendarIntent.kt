package com.whatever.caramel.feature.calendar.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface CalendarIntent : UiIntent {

    data object ShowDropDownMenu : CalendarIntent

    data object HideDropDownMenu : CalendarIntent

    data object DismissDropDownMenu : CalendarIntent

    data class ChangeYearPicker(
        val year: Int
    ) : CalendarIntent

    data class ChangeMonthPicker(
        val month: Int
    ) : CalendarIntent

    data object RefreshCalendar : CalendarIntent

    data class SwipeCalendar(
        val page: Int
    ) : CalendarIntent

}
