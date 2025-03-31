package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.user.UserProfileResponse
import com.whatever.caramel.core.remote.dto.user.UserStatusDto

fun String.toUserStatus() : UserStatus {
    return UserStatus.entries.find { it.name == this } ?: UserStatus.NONE
}

fun UserStatusDto.toUserStatus() = when(this) {
    UserStatusDto.NEW -> UserStatus.NEW
    UserStatusDto.SINGLE -> UserStatus.SINGLE
    UserStatusDto.COUPLED -> UserStatus.COUPLED
}

fun UserProfileResponse.toUser() = User(
    id = this.id,
    userStatus = this.userStatus.toUserStatus(),
    userProfile = UserProfile(
        nickName = this.nickname,
        gender = Gender.NONE,
        birthdayMillisecond = 0L
    )
)