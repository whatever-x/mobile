package com.whatever.caramel.feature.content.detail.mvi

import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ContentDetailSideEffect : UiSideEffect {
    data object NavigateToBackStack : ContentDetailSideEffect

    data class NavigateToEdit(
        val contentId: Long,
        val type: ContentType,
    ) : ContentDetailSideEffect

    data class ShowErrorSnackBar(
        val message: String,
    ) : ContentDetailSideEffect

    data class ShowErrorDialog(
        val message: String,
        val description: String?,
    ) : ContentDetailSideEffect
}
