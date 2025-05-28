package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ContentIntent : UiIntent {
    data object ClickCloseButton : ContentIntent

    data object ClickSaveButton : ContentIntent

    data class InputTitle(val text: String) : ContentIntent

    data class InputContent(val text: String) : ContentIntent

    data class ClickTag(val tag: Tag) : ContentIntent

    data class SelectCreateMode(val createMode: ContentState.CreateMode) : ContentIntent

    data object ClickDate : ContentIntent
    data object ClickTime : ContentIntent

    data object HideDateTimeDialog : ContentIntent

    data class OnYearChanged(val year: Int) : ContentIntent
    data class OnMonthChanged(val month: Int) : ContentIntent
    data class OnDayChanged(val day: Int) : ContentIntent

    data class OnPeriodChanged(val period: String) : ContentIntent
    data class OnHourChanged(val hour: String) : ContentIntent
    data class OnMinuteChanged(val minute: String) : ContentIntent

    data object ClickEditDialogLeftButton : ContentIntent
    data object ClickEditDialogRightButton : ContentIntent
}