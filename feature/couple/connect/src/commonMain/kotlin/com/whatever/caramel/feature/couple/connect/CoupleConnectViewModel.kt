package com.whatever.caramel.feature.couple.connect

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectIntent
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectSideEffect
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectState

class CoupleConnectViewModel(
    private val connectCoupleUseCase: ConnectCoupleUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CoupleConnectState, CoupleConnectSideEffect, CoupleConnectIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleConnectState {
        return CoupleConnectState()
    }

    override suspend fun handleIntent(intent: CoupleConnectIntent) {
        when (intent) {
            is CoupleConnectIntent.ClickConnectButton -> connectCouple()
            is CoupleConnectIntent.ClickBackButton -> postSideEffect(CoupleConnectSideEffect.NavigateToInviteCouple)
            is CoupleConnectIntent.ChangeInvitationCode -> changeInvitationCode(code = intent.invitationCode)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)

        val exception = throwable as CaramelException

        when (exception.code) {
            CoupleErrorCode.INVITATION_CODE_EXPIRED -> {
                postSideEffect(CoupleConnectSideEffect.ShowSnackBarMessage(message = exception.message))
            }
        }
    }

    private suspend fun connectCouple() {
        launch {
            connectCoupleUseCase(invitationCode = currentState.invitationCode)
            postSideEffect(CoupleConnectSideEffect.NavigateToMain)
        }
    }

    private fun changeInvitationCode(code: String) {
        reduce {
            copy(
                invitationCode = code
            )
        }
    }

}