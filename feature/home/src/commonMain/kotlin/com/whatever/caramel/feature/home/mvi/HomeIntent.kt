package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface HomeIntent : UiIntent {

    data object ClickSettingButton : HomeIntent

    data object ClickStartedCoupleDayButton : HomeIntent

    data object ClickCreateTodoItem : HomeIntent

    data object ClickTodoItem : HomeIntent

}