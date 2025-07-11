package com.whatever.caramel.feature.couple.connect

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectIntent
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectSideEffect
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectState

class CoupleConnectViewModel(
    private val connectCoupleUseCase: ConnectCoupleUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<CoupleConnectState, CoupleConnectSideEffect, CoupleConnectIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): CoupleConnectState = CoupleConnectState()

    override suspend fun handleIntent(intent: CoupleConnectIntent) {
        when (intent) {
            is CoupleConnectIntent.ClickConnectButton -> connectCouple()
            is CoupleConnectIntent.ClickBackButton -> postSideEffect(CoupleConnectSideEffect.NavigateToInviteCouple)
            is CoupleConnectIntent.ChangeInvitationCode -> changeInvitationCode(code = intent.invitationCode)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        CoupleConnectSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )
                ErrorUiType.DIALOG ->
                    postSideEffect(
                        CoupleConnectSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                CoupleConnectSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null
                ),
            )
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
                invitationCode = code,
            )
        }
    }
}
