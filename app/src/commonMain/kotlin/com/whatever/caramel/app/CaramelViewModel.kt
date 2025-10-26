package com.whatever.caramel.app

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.deeplink.DeepLinkHandler
import com.whatever.caramel.core.deeplink.model.AppsFlyerDeepLinkValue
import com.whatever.caramel.core.deeplink.model.CaramelDeepLink
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.usecase.app.CheckInAppReviewAvailableUseCase
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.mvi.AppIntent
import com.whatever.caramel.mvi.AppSideEffect
import com.whatever.caramel.mvi.AppState

class CaramelViewModel(
    private val connectCoupleUseCase: ConnectCoupleUseCase,
    private val deepLinkHandler: DeepLinkHandler,
    private val checkInAppReviewAvailableUseCase: CheckInAppReviewAvailableUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<AppState, AppSideEffect, AppIntent>(savedStateHandle, crashlytics) {
    init {
        observeDeepLink()
        observeInAppReview()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): AppState = AppState()

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.code) {
                CoupleErrorCode.INVITATION_CODE_EXPIRED -> {
                    reduce {
                        copy(
                            isShowErrorDialog = true,
                            dialogMessage = throwable.message,
                        )
                    }
                }
                // @ham2174 TODO : 커플 연결 실패시 현재 상태를 나타내는 안내 필요
                // ex) UserStatus == NONE 일 때 Token이 없기 때문에 로그인을 하라는 예외 메세지 받기
                // ex) UserStatus == NEW 일 때 프로필을 생성해달라는 예외 메세지 받기
                // ex) UserStatus == COUPLED 일 때 커플 상태이므로 연결이 불가능하다는 예외 메세지 받기
            }
        } else {
            caramelCrashlytics.recordException(throwable)
        }
    }

    override suspend fun handleIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.NavigateToStartDestination -> startDestination(userStatus = intent.userStatus)
            is AppIntent.CloseErrorDialog -> reduce { copy(isShowErrorDialog = false) }
            is AppIntent.ShowErrorDialog ->
                showErrorDialog(
                    message = intent.message,
                    description = intent.description,
                )

            is AppIntent.ShowToast -> postSideEffect(AppSideEffect.ShowToast(intent.message))
        }
    }

    private fun observeDeepLink() {
        launch {
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

    private fun observeInAppReview() {
        launch {
            checkInAppReviewAvailableUseCase().collect { isAvailable ->
                if (isAvailable) postSideEffect(AppSideEffect.RequestInAppReview)
            }
        }
    }

    private fun showErrorDialog(
        message: String,
        description: String?,
    ) {
        reduce {
            copy(
                isShowErrorDialog = true,
                dialogMessage = message,
                dialogDescription = description ?: "",
            )
        }
    }

    private suspend fun startDestination(userStatus: UserStatus) {
        launch {
            when (userStatus) {
                UserStatus.NONE -> postSideEffect(AppSideEffect.NavigateToLogin)
                UserStatus.NEW -> postSideEffect(AppSideEffect.NavigateToCreateProfile)
                UserStatus.SINGLE -> {
                    if (deepLinkHandler.deepLinkData?.first == AppsFlyerDeepLinkValue.INVITE) {
                        val inviteCode = deepLinkHandler.deepLinkData?.second?.get(0) ?: ""
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
                        val inviteCode = deepLinkHandler.deepLinkData?.second?.get(0) ?: ""
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
