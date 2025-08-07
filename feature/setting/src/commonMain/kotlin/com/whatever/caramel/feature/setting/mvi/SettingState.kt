package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState

data class SettingState(
    val isLoading: Boolean = true,
    val startDate: String = "",
    val myInfo: CoupleUser = CoupleUser(),
    val partnerInfo: CoupleUser = CoupleUser(),
    val isShowEditProfileBottomSheet: Boolean = false,
    val isShowLogoutDialog: Boolean = false,
    val isShowUserCancelledDialog: Boolean = false,
    val isNotificationEnabled: Boolean = false,
) : UiState

data class CoupleUser(
    val id: Long = 0L,
    val nickname: String = "",
    val birthday: String = "",
    val gender: Gender = Gender.IDLE,
) {
    companion object {
        fun toCoupleInfo(user: User?): CoupleUser {
            return if (user == null) CoupleUser()
            else CoupleUser(
                id = user.id,
                birthday = user.userProfile.birthday,
                nickname = user.userProfile.nickName,
                gender = user.userProfile.gender,
            )
        }
    }
}
