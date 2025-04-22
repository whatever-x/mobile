package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface HomeIntent : UiIntent {

    data object ClickSettingButton : HomeIntent

    data object ShowShareMessageEditBottomSheet : HomeIntent

    data object HideShareMessageEditBottomSheet : HomeIntent

    data object SaveShareMessage : HomeIntent

    data object ClearShareMessage : HomeIntent

    data object CreateTodoContent : HomeIntent

    data class ClickTodoContent(val todoContentId: Long) : HomeIntent

    data object PullToRefresh : HomeIntent

    data class ChangeShareMessage(val message: String) : HomeIntent

    data object ClickAnniversaryNudgeCard : HomeIntent

}