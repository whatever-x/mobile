package com.whatever.caramel.feature.content.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ContentSideEffect : UiSideEffect {

    data object NavigateToBackStack : ContentSideEffect

}