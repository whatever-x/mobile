package com.whatever.caramel.feature.copule.invite

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInvitationCodeUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteIntent
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteSideEffect
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteState

class CoupleInviteViewModel(
    private val coupleRepository: CoupleRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CoupleInviteState, CoupleInviteSideEffect, CoupleInviteIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleInviteState {
        return CoupleInviteState()
    }

    override suspend fun handleIntent(intent: CoupleInviteIntent) {
        when (intent) {
            is CoupleInviteIntent.ClickConnectCoupleButton -> connectCouple()
            is CoupleInviteIntent.ClickCloseButton -> close()
            is CoupleInviteIntent.ClickCopyInviteCodeButton -> copyInviteCode()
            is CoupleInviteIntent.ClickInviteButton -> sendInvite()
        }
    }

    private fun connectCouple() {
        postSideEffect(CoupleInviteSideEffect.NavigateToConnectCouple)
    }

    private fun close() {
        postSideEffect(CoupleInviteSideEffect.NavigateToLogin)
    }

    private suspend fun copyInviteCode() {
        launch {
            val inviteCode = coupleRepository.getCoupleInvitationCode().invitationCode
            postSideEffect(CoupleInviteSideEffect.CopyToClipBoardWithShowSnackBar(inviteCode = inviteCode))
        }
    }

    private suspend fun sendInvite() {
        launch {
            val inviteCode = coupleRepository.getCoupleInvitationCode().invitationCode
            postSideEffect(CoupleInviteSideEffect.ShareOfInvite(inviteCode = inviteCode))
        }
    }

}