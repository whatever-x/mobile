package com.whatever.caramel.feature.content.edit.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ContentEditIntent : UiIntent {
    data class OnTitleChanged(
        val title: String,
    ) : ContentEditIntent

    data class OnContentChanged(
        val content: String,
    ) : ContentEditIntent

    data object OnSaveClicked : ContentEditIntent

    data object OnDeleteClicked : ContentEditIntent

    data object OnBackClicked : ContentEditIntent

    data object DismissExitDialog : ContentEditIntent

    data object ConfirmExitDialog : ContentEditIntent

    data object DismissDeleteDialog : ContentEditIntent

    data object ConfirmDeleteDialog : ContentEditIntent

    data object DismissDeletedContentDialog : ContentEditIntent

    data object ClickCompleteButton : ContentEditIntent

    data object ClickAllDayButton : ContentEditIntent

    data class ClickAssignee(
        val assignee: ContentAssigneeUiModel,
    ) : ContentEditIntent

    data class ClickTag(
        val tag: Tag,
    ) : ContentEditIntent

    data class ClickDate(
        val type: ScheduleDateTimeType,
    ) : ContentEditIntent

    data class ClickTime(
        val type: ScheduleDateTimeType,
    ) : ContentEditIntent

    data object HideDateTimeDialog : ContentEditIntent

    data class OnYearChanged(
        val year: Int,
    ) : ContentEditIntent

    data class OnMonthChanged(
        val month: Int,
    ) : ContentEditIntent

    data class OnDayChanged(
        val day: Int,
    ) : ContentEditIntent

    data class OnPeriodChanged(
        val period: String,
    ) : ContentEditIntent

    data class OnHourChanged(
        val hour: String,
    ) : ContentEditIntent

    data class OnMinuteChanged(
        val minute: String,
    ) : ContentEditIntent

    data class OnCreateModeSelected(
        val mode: CreateMode,
    ) : ContentEditIntent
}
