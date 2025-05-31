package com.whatever.caramel.feature.content.detail.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface ContentDetailIntent : UiIntent {
    data object ClickCloseButton : ContentDetailIntent
    data object ClickEditButton : ContentDetailIntent
    data object ClickDeleteButton : ContentDetailIntent
    data object ClickConfirmDeleteDialogButton : ContentDetailIntent
    data object ClickCancelDeleteDialogButton : ContentDetailIntent
} 