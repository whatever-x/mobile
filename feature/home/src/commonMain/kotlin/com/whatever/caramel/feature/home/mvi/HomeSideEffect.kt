package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface HomeSideEffect : UiSideEffect {

    data object NavigateToSetting : HomeSideEffect

    data object NavigateToCreateTodo : HomeSideEffect

    data object NavigateToTodoDetail : HomeSideEffect

    data object NavigateToStartedCoupleDay : HomeSideEffect

}