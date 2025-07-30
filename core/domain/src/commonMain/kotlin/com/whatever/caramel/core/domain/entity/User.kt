package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.vo.user.UserAgreement
import com.whatever.caramel.core.domain.vo.user.UserProfile
import com.whatever.caramel.core.domain.vo.user.UserStatus

data class User(
    val id: Long,
    val userStatus: UserStatus = UserStatus.NONE,
    val userProfile: UserProfile? = null,
    val userAgreement: UserAgreement? = null,
) {
    val requireProfile: UserProfile
        get() =
            userProfile ?: throw CaramelException(
                code = AppErrorCode.NULL_VALUE,
                message = "사용자 프로필이 존재하지 않습니다",
                debugMessage = null,
                errorUiType = ErrorUiType.TOAST,
            )
}
