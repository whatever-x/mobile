package com.whatever.caramel.feature.couple.connect.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class CoupleConnectState(
    val invitationCode: String = "",
) : UiState {

    val isButtonEnabled: Boolean
        get() = invitationCode.isNotEmpty()
    
}