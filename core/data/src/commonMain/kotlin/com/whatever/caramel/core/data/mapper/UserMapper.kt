package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.user.response.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.response.UserProfileResponse
import com.whatever.caramel.core.remote.dto.user.UserStatusDto
import com.whatever.caramel.core.util.DateParser.toMillisecond

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
        gender = Gender.IDLE,
        birthdayMillisecond = 0L
    )
)

fun EditUserProfileResponse.toUser() = User(
    id = this.id,
    userStatus = UserStatus.NONE,
    userProfile = UserProfile(
        nickName = this.nickname,
        gender = Gender.IDLE,
        birthdayMillisecond = this.birthday.toMillisecond() ?: 0L
    )
)