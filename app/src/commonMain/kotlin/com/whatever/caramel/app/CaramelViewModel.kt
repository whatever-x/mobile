package com.whatever.caramel.app

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.whatever.caramel.core.deeplink.DeepLinkHandler
import com.whatever.caramel.core.deeplink.model.AppsFlyerDeepLinkValue
import com.whatever.caramel.core.deeplink.model.CaramelDeepLink
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.domain.usecase.user.GetUserStatusUseCase
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.mvi.AppIntent
import com.whatever.caramel.mvi.AppSideEffect
import com.whatever.caramel.mvi.AppState
import kotlinx.coroutines.launch

class CaramelViewModel(
    private val connectCoupleUseCase: ConnectCoupleUseCase,
    private val deepLinkHandler: DeepLinkHandler,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AppState, AppSideEffect, AppIntent>(savedStateHandle) {

    init {
        viewModelScope.launch {
            deepLinkHandler.deepLinkFlow.collect { deepLink ->
                when (deepLink) {
                    is CaramelDeepLink.Invite -> {
                        this@CaramelViewModel.launch {
                            tryToConnectCouple(inviteCode = deepLink.code)
                        }
                    }
                    is CaramelDeepLink.Unknown -> TODO()
                }
            }
        }
    }

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
            // @ham2174 TODO : 커플 연결 실패시 현재 상태를 나타내는 안내 필요
            // ex) UserStatus == NONE 일 때 Token이 없기 때문에 로그인을 하라는 예외 메세지 받기
            // ex) UserStatus == NEW 일 때 프로필을 생성해달라는 예외 메세지 받기
            // ex) UserStatus == COUPLED 일 때 커플 상태이므로 연결이 불가능하다는 예외 메세지 받기
        }
    }

    override suspend fun handleIntent(intent: AppIntent) {
        when(intent) {
            is AppIntent.NavigateToStartDestination -> startDestination(userStatus = intent.userStatus)
            is AppIntent.CloseErrorDialog -> reduce { copy(isShowErrorDialog = false) }
        }
    }

    private suspend fun startDestination(userStatus: UserStatus) {
        launch {
            when (userStatus) {
                UserStatus.NONE -> postSideEffect(AppSideEffect.NavigateToLogin)
                UserStatus.NEW -> postSideEffect(AppSideEffect.NavigateToCreateProfile)
                UserStatus.SINGLE -> {
                    if (deepLinkHandler.deepLinkData?.first == AppsFlyerDeepLinkValue.INVITE) {
                        val inviteCode = deepLinkHandler.deepLinkData?.second?.get(0)?: ""
                        runCatching {
                            tryToConnectCouple(inviteCode = inviteCode)
                        }.onFailure { throwable ->
                            postSideEffect(AppSideEffect.NavigateToInviteCoupleScreen)
                            throw throwable
                        }
                    } else {
                        postSideEffect(AppSideEffect.NavigateToInviteCoupleScreen)
                    }
                }
                UserStatus.COUPLED -> {
                    if (deepLinkHandler.deepLinkData?.first == AppsFlyerDeepLinkValue.INVITE) {
                        val inviteCode = deepLinkHandler.deepLinkData?.second?.get(0)?: ""
                        runCatching {
                            tryToConnectCouple(inviteCode = inviteCode)
                        }.onFailure { throwable ->
                            postSideEffect(AppSideEffect.NavigateToMain)
                            throw throwable
                        }
                    } else {
                        postSideEffect(AppSideEffect.NavigateToMain)
                    }
                }
            }
        }
    }

    private suspend fun tryToConnectCouple(inviteCode: String) {
        connectCoupleUseCase(invitationCode = inviteCode)
        deepLinkHandler.clearDeepLinkData()
        postSideEffect(AppSideEffect.NavigateToConnectingCoupleScreen)
    }

}
