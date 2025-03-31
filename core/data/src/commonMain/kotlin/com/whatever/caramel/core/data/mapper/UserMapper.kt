package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.core.remote.dto.user.UserStatusDto

fun String.toUserStatus() : UserStatus {
    return UserStatus.entries.find { it.name == this } ?: UserStatus.NONE
}

fun UserStatusDto.toUserStatus() = when(this) {
    UserStatusDto.NEW -> UserStatus.NEW
    UserStatusDto.SINGLE -> UserStatus.SINGLE
    UserStatusDto.COUPLED -> UserStatus.COUPLED
}