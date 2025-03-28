package com.whatever.caramel.feature.copule.invite

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteIntent
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteSideEffect
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteState

class CoupleInviteViewModel(
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

    private fun copyInviteCode() {
        // @ham2174 TODO : 초대 코드 불러오기 UseCase 호출
        val inviteCode = "테스트 코드"
        postSideEffect(CoupleInviteSideEffect.CopyToClipBoardWithShowSnackBar(inviteCode = inviteCode))
    }

    private fun sendInvite() {
        postSideEffect(CoupleInviteSideEffect.ShareOfInvite)
    }

}