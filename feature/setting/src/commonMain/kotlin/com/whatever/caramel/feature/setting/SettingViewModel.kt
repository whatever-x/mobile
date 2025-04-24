package com.whatever.caramel.feature.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.auth.LogoutUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.setting.mvi.CoupleUser
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import com.whatever.caramel.feature.setting.mvi.SettingState

class SettingViewModel(
    private val getCoupleInfoUseCase: GetCoupleInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SettingState, SettingSideEffect, SettingIntent>(savedStateHandle) {

    init {
        getCoupleInfo()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): SettingState {
        return SettingState()
    }

    override suspend fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.ClickSettingBackButton -> postSideEffect(SettingSideEffect.NavigateToHome)
            is SettingIntent.ToggleEditProfile -> toggleEditProfile()
            SettingIntent.ToggleLogout -> toggleLogoutButton()
            SettingIntent.ClickPrivacyPolicyButton -> postSideEffect(SettingSideEffect.NavigateToPersonalInfoTermNotion)
            SettingIntent.ClickTermsOfServiceButtons -> postSideEffect(SettingSideEffect.NavigateToServiceTermNotion)
            SettingIntent.ClickLogoutConfirmButton -> logout()
            SettingIntent.ClickEditBirthDayButton -> postSideEffect(SettingSideEffect.NavigateToEditBirthDay)
            SettingIntent.ClickEditNicknameButton -> postSideEffect(SettingSideEffect.NavigateToEditNickname)
            SettingIntent.ClickEditCountDownButton -> postSideEffect(SettingSideEffect.NavigateToEditCountDown(currentState.startDateTimeMillisecond))
            SettingIntent.ToggleUserCancelledButton -> toggleUserCancelledButton()
            SettingIntent.ClickAppUpdateButton -> TODO("앱 업데이트 기능 확인 필요")
            SettingIntent.ClickUserCancelledConfirmButton -> TODO("탈퇴하기 API 추가 후 연동")
        }
    }

    private fun toggleUserCancelledButton() {
        reduce {
            copy(
                isShowUserCancelledDialog = !isShowUserCancelledDialog
            )
        }
    }

    private fun toggleLogoutButton(){
        reduce {
            copy(
                isShowLogoutDialog = !isShowLogoutDialog
            )
        }
    }

    private fun toggleEditProfile() {
        reduce {
            copy(
                isShowEditProfileBottomSheet = !isShowEditProfileBottomSheet
            )
        }
    }

    private fun logout() {
        launch {
            logoutUseCase()
            postSideEffect(SettingSideEffect.NavigateLogin)
        }
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
}