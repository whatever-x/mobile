package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface HomeSideEffect : UiSideEffect {

    data object NavigateToSetting : HomeSideEffect

    data object NavigateToCreateContent : HomeSideEffect

    data class NavigateToContentDetail(val contentId: Long) : HomeSideEffect

    data object NavigateToEditAnniversary : HomeSideEffect

}