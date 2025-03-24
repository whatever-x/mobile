package com.whatever.caramel.core.domain.entity.user

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
    val requireId get() = id ?: throw NullPointerException("user id is null")
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
