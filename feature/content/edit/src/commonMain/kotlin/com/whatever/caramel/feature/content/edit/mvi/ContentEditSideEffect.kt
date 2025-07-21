package com.whatever.caramel.feature.content.edit.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ContentEditSideEffect : UiSideEffect {
    data object NavigateBack : ContentEditSideEffect

    data object NavigateBackToContentList : ContentEditSideEffect

    data class ShowErrorSnackBar(
        val message: String,
    ) : ContentEditSideEffect

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : ContentEditSideEffect
}
