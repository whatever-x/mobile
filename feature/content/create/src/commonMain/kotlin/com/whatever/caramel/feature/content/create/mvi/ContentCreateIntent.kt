package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.viewmodel.UiIntent
import com.whatever.caramel.core.ui.content.CreateMode

sealed interface ContentCreateIntent : UiIntent {
    data object ClickCloseButton : ContentCreateIntent

    data object ClickSaveButton : ContentCreateIntent

    data class InputTitle(val text: String) : ContentCreateIntent

    data class InputContent(val text: String) : ContentCreateIntent

    data class ClickTag(val tag: Tag) : ContentCreateIntent

    data class SelectCreateMode(val createMode: CreateMode) : ContentCreateIntent

    data object ClickDate : ContentCreateIntent
    data object ClickTime : ContentCreateIntent

    data object HideDateTimeDialog : ContentCreateIntent

    data class OnYearChanged(val year: Int) : ContentCreateIntent
    data class OnMonthChanged(val month: Int) : ContentCreateIntent
    data class OnDayChanged(val day: Int) : ContentCreateIntent

    data class OnPeriodChanged(val period: String) : ContentCreateIntent
    data class OnHourChanged(val hour: String) : ContentCreateIntent
    data class OnMinuteChanged(val minute: String) : ContentCreateIntent

    data object ClickEditDialogLeftButton : ContentCreateIntent
    data object ClickEditDialogRightButton : ContentCreateIntent
}