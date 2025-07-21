package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface HomeIntent : UiIntent {
    data object LoadDataOnStart : HomeIntent

    data object ClickSettingButton : HomeIntent

    data object ShowShareMessageEditBottomSheet : HomeIntent

    data object HideShareMessageEditBottomSheet : HomeIntent

    data object SaveShareMessage : HomeIntent

    data object CreateTodoContent : HomeIntent

    data class ClickTodoContent(
        val todoContentId: Long,
    ) : HomeIntent

    data object PullToRefresh : HomeIntent

    data object ClickAnniversaryNudgeCard : HomeIntent

    data class ClickBalanceGameOptionButton(
        val option: BalanceGameOptionState,
    ) : HomeIntent

    data object ChangeBalanceGameCardState : HomeIntent

    data object HideDialog : HomeIntent

    data class InputShareMessage(
        val newShareMessage: String,
    ) : HomeIntent

    data object ClearShareMessage : HomeIntent
}
