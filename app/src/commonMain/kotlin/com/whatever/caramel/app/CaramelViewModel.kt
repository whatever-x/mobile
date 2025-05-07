package com.whatever.caramel.app

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.domain.usecase.user.GetUserStatusUseCase
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.deeplink.CaramelDeepLink
import com.whatever.caramel.deeplink.DeepLinkHelper.parseCaramelDeepLink
import com.whatever.caramel.mvi.AppIntent
import com.whatever.caramel.mvi.AppSideEffect
import com.whatever.caramel.mvi.AppState

class CaramelViewModel(
    private val getUserStatusUseCase: GetUserStatusUseCase,
    private val connectCoupleUseCase: ConnectCoupleUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AppState, AppSideEffect, AppIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): AppState =
        AppState()

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)

        val exception = throwable as CaramelException

        when (exception.code) {
            CoupleErrorCode.INVITATION_CODE_EXPIRED -> {
                reduce {
                    copy(
                        isShowErrorDialog = true,
                        dialogMessage = exception.message
                    )
                }
            }
        }

        when (currentState.userStatus) { // @ham2174 FIXME : 예외 코드 분기 로직 내부에 처리
            UserStatus.COUPLED -> postSideEffect(AppSideEffect.NavigateToMain)
            UserStatus.SINGLE -> postSideEffect(AppSideEffect.NavigateToInviteCoupleScreen)
            else -> {}
        }
    }

    override suspend fun handleIntent(intent: AppIntent) {
        when(intent) {
            is AppIntent.AcceptInvitation -> setDeepLinkInviteCode(inviteCode = intent.inviteCode)
            is AppIntent.ReceiveNewIntentData -> handleNewIntentData(data = intent.data)
            is AppIntent.NavigateToStartDestination -> startDestination()
            is AppIntent.CloseErrorDialog -> reduce { copy(isShowErrorDialog = false) }
        }
    }

    private suspend fun handleNewIntentData(data: String) {
        launch {
            val caramelDeepLink = parseCaramelDeepLink(uri = data)

            when (caramelDeepLink) {
                is CaramelDeepLink.InviteCode -> {
                    setUserStatus()

                    when (currentState.userStatus) {
                        UserStatus.NONE,
                        UserStatus.NEW -> { setDeepLinkInviteCode(inviteCode = caramelDeepLink.code) }
                        UserStatus.SINGLE,
                        UserStatus.COUPLED-> { tryToConnectCouple(inviteCode = caramelDeepLink.code) }
                    }
                }
                is CaramelDeepLink.Unknown -> { } // @ham2174 FIXME : 존재하지 않는 딥링크 처리
            }
        }
    }

    private suspend fun startDestination() {
        launch {
            val inviteCode = savedStateHandle.get<String>(INVITE_CODE)
            setUserStatus()

            when (currentState.userStatus) {
                UserStatus.NONE -> postSideEffect(AppSideEffect.NavigateToLogin)
                UserStatus.NEW -> postSideEffect(AppSideEffect.NavigateToCreateProfile)
                UserStatus.SINGLE -> {
                    if (inviteCode != null) {
                        tryToConnectCouple(inviteCode = inviteCode)
                    } else {
                        postSideEffect(AppSideEffect.NavigateToInviteCoupleScreen)
                    }
                }
                UserStatus.COUPLED -> {
                    if (inviteCode != null) {
                        tryToConnectCouple(inviteCode = inviteCode)
                    } else {
                        postSideEffect(AppSideEffect.NavigateToMain)
                    }
                }
            }
        }
    }

    private fun setDeepLinkInviteCode(inviteCode: String) {
        savedStateHandle[INVITE_CODE] = inviteCode
    }

    private fun setUserStatus() { // @ham2174 FIXME : 추후 RoomDB 확장 시 Flow 데이터로 변경
        launch {
            val userStatus = getUserStatusUseCase()
            reduce { copy(userStatus = userStatus) }
        }
    }

    private suspend fun tryToConnectCouple(inviteCode: String) {
        launch {
            connectCoupleUseCase(invitationCode = inviteCode)
            postSideEffect(AppSideEffect.NavigateToConnectingCoupleScreen)
        }
    }

    companion object {
        private const val INVITE_CODE = "inviteCode"
    }

}
