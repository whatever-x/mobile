package com.whatever.caramel.feature.setting

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.setting.mvi.CoupleUser
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import com.whatever.caramel.feature.setting.mvi.SettingState

class SettingViewModel(
    private val getCoupleInfoUseCase: GetCoupleInfoUseCase,
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
            is SettingIntent.ClickEditBirthdayButton -> postSideEffect(SettingSideEffect.NavigateToEditBirthday)
            is SettingIntent.ClickEditNicknameButton -> postSideEffect(SettingSideEffect.NavigateToEditNickName)
            SettingIntent.ClickCancelButton -> TODO()
            SettingIntent.ClickLogoutButton -> TODO()
            SettingIntent.ClickPrivacyPolicyButton -> TODO()
            SettingIntent.ClickTermsOfServiceButtons -> TODO()
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