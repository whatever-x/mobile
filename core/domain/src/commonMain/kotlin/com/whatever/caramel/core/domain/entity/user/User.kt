package com.whatever.caramel.core.domain.entity.user

import com.whatever.caramel.core.domain.exception.AppExceptionCode
import com.whatever.caramel.core.domain.exception.CaramelException
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

data class User(
    val userBasic: UserBasic,
    val userProfile: UserProfile? = null,
    val userMetaData: UserMetaData? = null,
)

data class UserBasic(
    private val id : Long? = null,
    val coupleId : Long? = null,
    val userStatus: UserStatus = UserStatus.NONE
) {
    val hasId get() = id != null
    val requireId get() = id ?: throw CaramelException(
        code = AppExceptionCode.NULL_VALUE,
        message = "사용자 ID가 존재하지 않습니다",
        debugMessage = null
    )
}

data class UserProfile(
    val nickName : Nickname,
    val gender : Gender,
    val birthday : String
)

data class UserMetaData(
    val agreementServiceTerms: Boolean = false,
    val agreementPrivacyPolicy: Boolean = false
)
