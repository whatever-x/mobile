package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.util.toFormattedDate
import com.whatever.caramel.core.viewmodel.UiState

data class SettingState(
    val isLoading: Boolean = true,
    val startDateTimeMillisecond: Long = 0L,
    val myInfo: CoupleUser = CoupleUser(),
    val partnerInfo: CoupleUser = CoupleUser(),
    val isShowProfileChangeBottomSheet: Boolean = false,
    val isShowLogoutDialog : Boolean = false,
    val isShowUserCancelledDialog : Boolean = false
) : UiState {
    val startDate: String
        get() = if (startDateTimeMillisecond == 0L) {
            "언제부터 사귀기 시작했나요?"
        } else {
            startDateTimeMillisecond.toFormattedDate() ?: ""
        }
}

data class CoupleUser(
    val id: Long = 0L,
    val nickname: String = "",
    val birthDayTimeMillisecond: Long = 0L,
    val gender: Gender = Gender.IDLE
) {
    val birthDate: String
        get() = birthDayTimeMillisecond.toFormattedDate() ?: ""

    companion object {
        fun toCoupleInfo(user: User) = CoupleUser(
            id = user.requireId,
            birthDayTimeMillisecond = user.requireProfile.birthdayMillisecond,
            nickname = user.requireProfile.nickName,
            gender = user.requireProfile.gender
        )
    }
}