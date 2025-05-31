package com.whatever.caramel.feature.content.edit.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ContentEditSideEffect : UiSideEffect {
    data object NavigateBack : ContentEditSideEffect
    data class ShowErrorSnackBar(val code: String, val message: String? = null) : ContentEditSideEffect
} 