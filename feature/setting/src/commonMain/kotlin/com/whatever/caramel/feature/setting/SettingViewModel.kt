package com.whatever.caramel.feature.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.auth.LogoutUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.setting.mvi.CoupleUser
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import com.whatever.caramel.feature.setting.mvi.SettingState
import io.github.aakira.napier.Napier

class SettingViewModel(
    private val getCoupleInfoUseCase: GetCoupleInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SettingState, SettingSideEffect, SettingIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): SettingState {
        return SettingState()
    }

    override suspend fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.ClickSettingBackButton -> postSideEffect(SettingSideEffect.NavigateToHome)
            is SettingIntent.ToggleEditProfile -> toggleEditProfileBottomSheet()
            SettingIntent.RefreshCoupleData -> getCoupleInfo()
            SettingIntent.ToggleLogout -> toggleLogoutButton()
            SettingIntent.ClickPrivacyPolicyButton -> postSideEffect(SettingSideEffect.NavigateToPersonalInfoTermNotion)
            SettingIntent.ClickTermsOfServiceButtons -> postSideEffect(SettingSideEffect.NavigateToServiceTermNotion)
            SettingIntent.ClickLogoutConfirmButton -> logout()
            SettingIntent.ClickEditBirthDayButton -> naviateEditBirthday()
            SettingIntent.ClickEditNicknameButton -> navigateEditNickname()
            SettingIntent.ClickEditCountDownButton -> postSideEffect(
                SettingSideEffect.NavigateToEditCountDown(
                    startDateMillisecond = currentState.startDateTimeMillisecond
                )
            )
            SettingIntent.ToggleUserCancelledButton -> toggleUserCancelledButton()
            SettingIntent.ClickAppUpdateButton -> TODO("앱 업데이트 기능 확인 필요")
            SettingIntent.ClickUserCancelledConfirmButton -> TODO("탈퇴하기 API 추가 후 연동")
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        Napier.e { "exception: $throwable" }
    }

    private fun getCoupleInfo() {
        launch {
            reduce {
                copy(
                    isLoading = true
                )
            }
            val couple = getCoupleInfoUseCase()
            reduce {
                copy(
                    isLoading = false,
                    startDateTimeMillisecond = couple.info.startDateMillis,
                    myInfo = CoupleUser.toCoupleInfo(couple.myInfo),
                    partnerInfo = CoupleUser.toCoupleInfo(couple.partnerInfo),
                )
            }
        }
    }

    private fun toggleUserCancelledButton() {
        reduce {
            copy(
                isShowUserCancelledDialog = !isShowUserCancelledDialog
            )
        }
    }

    private fun toggleLogoutButton() {
        reduce {
            copy(
                isShowLogoutDialog = !isShowLogoutDialog
            )
        }
    }

    private fun toggleEditProfileBottomSheet() {
        reduce {
            copy(
                isShowEditProfileBottomSheet = !isShowEditProfileBottomSheet
            )
        }
    }

    private fun navigateEditNickname(){
        toggleEditProfileBottomSheet()
        postSideEffect(
            SettingSideEffect.NavigateToEditNickname(
                nickname = currentState.myInfo.nickname
            )
        )
    }

    private fun naviateEditBirthday(){
        toggleEditProfileBottomSheet()
        postSideEffect(
            SettingSideEffect.NavigateToEditBirthDay(
                birthdayMillisecond = currentState.myInfo.birthDayTimeMillisecond
            )
        )
    }

    private fun logout() {
        launch {
            logoutUseCase()
            postSideEffect(SettingSideEffect.NavigateLogin)
        }
    }

}