package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.viewmodel.UiSideEffect

sealed interface ContentCreateSideEffect : UiSideEffect {

    data object NavigateToBackStack : ContentCreateSideEffect

    data class ShowErrorSnackBar(val code: String, val message: String? = null) : ContentCreateSideEffect
    
    data class ShowToast(val message: String) : ContentCreateSideEffect
}