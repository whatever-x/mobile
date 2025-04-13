package com.whatever.caramel.feature.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.auth.LogoutUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.util.toMillisecond
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
            SettingIntent.ClickEditCountDownButton -> postSideEffect(SettingSideEffect.NavigateToEditCountDown(currentState.startDate.toMillisecond()))
            SettingIntent.ToggleUserCancelledButton -> toggleUserCancelledButton()
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
                isShowProfileChangeBottomSheet = !isShowProfileChangeBottomSheet
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
            val info = getCoupleInfoUseCase()
            reduce {
                copy(
                    isLoading = false,
                    startDateTimeMillisecond = info.startDateMillis,
                    myInfo = CoupleUser.toCoupleInfo(info.myInfo),
                    partnerInfo = CoupleUser.toCoupleInfo(info.partnerInfo),
                )
            }
        }
    }
}