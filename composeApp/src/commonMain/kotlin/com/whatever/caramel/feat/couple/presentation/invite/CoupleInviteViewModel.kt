package com.whatever.caramel.feat.couple.presentation.invite

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.couple.presentation.invite.mvi.CoupleInviteIntent
import com.whatever.caramel.feat.couple.presentation.invite.mvi.CoupleInviteSideEffect
import com.whatever.caramel.feat.couple.presentation.invite.mvi.CoupleInviteState

class CoupleInviteViewModel(
    savedStateHandle: SavedStateHandle

) : BaseViewModel<CoupleInviteState,CoupleInviteSideEffect, CoupleInviteIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleInviteState {
        return CoupleInviteState()
    }

    override suspend fun handleIntent(intent: CoupleInviteIntent) {
        when (intent) {
            is CoupleInviteIntent.ClickInputCodeButton -> postSideEffect(CoupleInviteSideEffect.NavigateToCoupleCode)
        }
    }

}