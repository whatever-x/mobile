package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.util.DateFormatter.toFormattedDate
import com.whatever.caramel.core.viewmodel.UiState

data class SettingState(
    val isLoading: Boolean = false,
    val startDate: String = "",
    val myInfo: CoupleUser = CoupleUser(),
    val partnerInfo: CoupleUser = CoupleUser(),
    val isShowEditProfileBottomSheet: Boolean = false,
    val isShowLogoutDialog: Boolean = false,
    val isShowUserCancelledDialog: Boolean = false
) : UiState

data class CoupleUser(
    val id: Long = 0L,
    val nickname: String = "",
    val birthday: String = "",
    val gender: Gender = Gender.IDLE
) {
    companion object {
        fun toCoupleInfo(user: User) = CoupleUser(
            id = user.requireId,
            birthday = user.requireProfile.birthday,
            nickname = user.requireProfile.nickName,
            gender = user.requireProfile.gender
        )
    }
}