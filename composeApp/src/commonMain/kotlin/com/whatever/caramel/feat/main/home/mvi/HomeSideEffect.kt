package com.whatever.caramel.feat.main.home.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface HomeSideEffect : UiSideEffect {

    data object NavigateToSetting : HomeSideEffect

    data object NavigateToCreateTodo : HomeSideEffect

    data object NavigateToTodoDetail : HomeSideEffect

    data object NavigateToStartedCoupleDay : HomeSideEffect

}