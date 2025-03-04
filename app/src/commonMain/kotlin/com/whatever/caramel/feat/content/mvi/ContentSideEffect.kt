package com.whatever.caramel.feat.content.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface ContentSideEffect : UiSideEffect {

    data object NavigateToBackStack : ContentSideEffect

}