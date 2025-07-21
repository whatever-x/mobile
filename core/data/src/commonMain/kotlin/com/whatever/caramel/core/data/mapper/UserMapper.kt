package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.User
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.user.GenderDto
import com.whatever.caramel.core.remote.dto.user.response.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.response.UserInfoResponse
import com.whatever.caramel.core.remote.dto.user.response.UserProfileResponse

fun UserProfileResponse.toUser() =
    User(
        id = this.id,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        userProfile =
            UserProfile(
                nickName = this.nickname,
                gender = Gender.IDLE,
                birthday = "",
            ),
    )

fun EditUserProfileResponse.toUser() =
    User(
        id = this.id,
        userStatus = UserStatus.NONE,
        userProfile =
            UserProfile(
                nickName = this.nickname,
                gender = Gender.IDLE,
                birthday = this.birthday.replace("-", "."),
            ),
    )

fun UserInfoResponse.toUser() =
    User(
        id = this.id,
        userStatus = UserStatus.valueOf(this.userStatus.name),
        userProfile =
            UserProfile(
                nickName = this.nickname ?: "",
                gender =
                    when (this.gender) {
                        GenderDto.MALE -> Gender.MALE
                        GenderDto.FEMALE -> Gender.FEMALE
                        else -> Gender.IDLE
                    },
                birthday = this.birthDate ?: "",
            ),
        userMetaData = null,
    )
