package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ContentSideEffect : UiSideEffect {

    data object NavigateToBackStack : ContentSideEffect

    data class ShowErrorSnackBar(val code: String, val message: String? = null) : ContentSideEffect
}