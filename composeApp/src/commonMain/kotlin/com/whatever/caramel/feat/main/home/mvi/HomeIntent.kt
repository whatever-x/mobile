package com.whatever.caramel.feat.main.home.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface HomeIntent : UiIntent {

    data object ClickSettingButton : HomeIntent

    data object ClickStartedCoupleDayButton : HomeIntent

    data object ClickCreateTodoItem : HomeIntent

    data object ClickTodoItem : HomeIntent

}