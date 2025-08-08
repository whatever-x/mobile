package com.whatever.caramel.feature.copule.invite

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInvitationCodeUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteIntent
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteSideEffect
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteState

class CoupleInviteViewModel(
    private val getCoupleInvitationCodeUseCase: GetCoupleInvitationCodeUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<CoupleInviteState, CoupleInviteSideEffect, CoupleInviteIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleInviteState = CoupleInviteState()

    override suspend fun handleIntent(intent: CoupleInviteIntent) {
        when (intent) {
            is CoupleInviteIntent.ClickConnectCoupleButton -> connectCouple()
            is CoupleInviteIntent.ClickCloseButton -> close()
            is CoupleInviteIntent.ClickCopyInviteCodeButton -> copyInviteCode()
            is CoupleInviteIntent.ClickInviteButton -> sendInvite()
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        CoupleInviteSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )
                ErrorUiType.DIALOG ->
                    postSideEffect(
                        CoupleInviteSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                CoupleInviteSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
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
            val inviteCode = getCoupleInvitationCodeUseCase().invitationCode
            postSideEffect(CoupleInviteSideEffect.CopyToClipBoardWithShowSnackBar(inviteCode = inviteCode))
        }
    }

    private suspend fun sendInvite() {
        launch {
            val inviteCode = getCoupleInvitationCodeUseCase().invitationCode
            postSideEffect(CoupleInviteSideEffect.ShareOfInvite(inviteCode = inviteCode))
        }
    }
}
