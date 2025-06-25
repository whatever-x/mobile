package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.vo.user.UserMetaData
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus

data class User(
    private val id: Long? = null,
    val userStatus: UserStatus = UserStatus.NONE,
    val userProfile: UserProfile? = null,
    val userMetaData: UserMetaData? = null,
) {
    val hasId get() = id != null
    val requireId
        get() = id ?: throw CaramelException(
            code = AppErrorCode.NULL_VALUE,
            message = "사용자 ID가 존재하지 않습니다",
            debugMessage = null,
            errorUiType = ErrorUiType.TOAST
        )

    val hasProfile get() = userProfile != null
    val requireProfile: UserProfile
        get() = userProfile ?: throw CaramelException(
            code = AppErrorCode.NULL_VALUE,
            message = "사용자 프로필이 존재하지 않습니다",
            debugMessage = null,
            errorUiType = ErrorUiType.TOAST
        )
}

