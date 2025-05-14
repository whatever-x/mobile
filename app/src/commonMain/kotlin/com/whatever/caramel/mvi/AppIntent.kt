package com.whatever.caramel.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface AppIntent : UiIntent {

    data object NavigateToStartDestination : AppIntent

    data object CloseErrorDialog : AppIntent

}