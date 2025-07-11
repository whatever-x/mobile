package com.whatever.caramel.feature.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.auth.LogoutUseCase
import com.whatever.caramel.core.domain.usecase.auth.SignOutUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleRelationshipInfoUseCase
import com.whatever.caramel.core.domain.usecase.user.GetUserSettingUseCase
import com.whatever.caramel.core.domain.usecase.user.UpdateUserSettingUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.setting.mvi.CoupleUser
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import com.whatever.caramel.feature.setting.mvi.SettingState
import kotlinx.coroutines.joinAll

class SettingViewModel(
    private val getCoupleRelationshipInfoUseCase: GetCoupleRelationshipInfoUseCase,
    private val updateUserSettingUseCase: UpdateUserSettingUseCase,
    private val getUserSettingUseCase: GetUserSettingUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val signOutUseCase: SignOutUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics
) : BaseViewModel<SettingState, SettingSideEffect, SettingIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): SettingState = SettingState()

    override suspend fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.ClickSettingBackButton -> postSideEffect(SettingSideEffect.NavigateToHome)
            is SettingIntent.ToggleEditProfile -> toggleEditProfileBottomSheet()
            SettingIntent.RefreshCoupleData -> getCoupleInfo()
            SettingIntent.ToggleLogout -> toggleLogoutButton()
            SettingIntent.ClickPrivacyPolicyButton -> postSideEffect(SettingSideEffect.OpenPrivacyPolicy)
            SettingIntent.ClickTermsOfServiceButtons -> postSideEffect(SettingSideEffect.OpenTermsOfService)
            SettingIntent.ClickLogoutConfirmButton -> logout()
            SettingIntent.ClickEditBirthDayButton -> navigateEditBirthday()
            SettingIntent.ClickEditNicknameButton -> navigateEditNickname()
            SettingIntent.ClickEditCountDownButton ->
                postSideEffect(
                    SettingSideEffect.NavigateToEditCountDown(
                        startDate = currentState.startDate,
                    ),
                )

            SettingIntent.ToggleUserCancelledButton -> toggleUserCancelledButton()
            SettingIntent.ClickAppUpdateButton -> TODO("앱 업데이트 기능 확인 필요")
            SettingIntent.ClickUserCancelledConfirmButton -> signOut()
            SettingIntent.ClickNotificationToggleButton -> toggleUpdateNotificationSetting()
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        SettingSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )
                ErrorUiType.DIALOG ->
                    postSideEffect(
                        SettingSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                SettingSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null
                ),
            )
        }
    }

    private fun toggleUpdateNotificationSetting() {
        launch {
            val result =
                updateUserSettingUseCase(
                    notificationEnabled = !currentState.isNotificationEnabled,
                )

            reduce { copy(isNotificationEnabled = result) }
        }
    }

    private fun signOut() {
        launch {
            signOutUseCase()
            postSideEffect(SettingSideEffect.NavigateLogin)
        }
    }

    private fun getCoupleInfo() {
        launch {
            reduce { copy(isLoading = true) }

            val coupleJob =
                launch {
                    val couple = getCoupleRelationshipInfoUseCase()

                    reduce {
                        copy(
                            isLoading = false,
                            startDate = couple.info.startDate,
                            myInfo = CoupleUser.toCoupleInfo(couple.myInfo),
                            partnerInfo = CoupleUser.toCoupleInfo(couple.partnerInfo),
                        )
                    }
                }

            val notificationEnabledJob =
                launch {
                    val notificationEnabled = getUserSettingUseCase()

                    reduce { copy(isNotificationEnabled = notificationEnabled) }
                }

            joinAll(coupleJob, notificationEnabledJob)
        }
    }

    private fun toggleUserCancelledButton() {
        reduce {
            copy(
                isShowUserCancelledDialog = !isShowUserCancelledDialog,
            )
        }
    }

    private fun toggleLogoutButton() {
        reduce {
            copy(
                isShowLogoutDialog = !isShowLogoutDialog,
            )
        }
    }

    private fun toggleEditProfileBottomSheet() {
        reduce {
            copy(
                isShowEditProfileBottomSheet = !isShowEditProfileBottomSheet,
            )
        }
    }

    private fun navigateEditNickname() {
        toggleEditProfileBottomSheet()
        postSideEffect(
            SettingSideEffect.NavigateToEditNickname(
                nickname = currentState.myInfo.nickname,
            ),
        )
    }

    private fun navigateEditBirthday() {
        toggleEditProfileBottomSheet()
        postSideEffect(
            SettingSideEffect.NavigateToEditBirthday(
                birthday = currentState.myInfo.birthday,
            ),
        )
    }

    private fun logout() {
        launch {
            logoutUseCase()
            postSideEffect(SettingSideEffect.NavigateLogin)
        }
    }
}
