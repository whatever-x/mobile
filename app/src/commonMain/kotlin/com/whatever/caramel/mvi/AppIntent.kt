package com.whatever.caramel.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface AppIntent : UiIntent {

    data class AcceptInvitation(val inviteCode: String) : AppIntent
    
    data class ReceiveNewIntentData(val data: String) : AppIntent

    data object NavigateToStartDestination : AppIntent

    data object CloseErrorDialog : AppIntent

}