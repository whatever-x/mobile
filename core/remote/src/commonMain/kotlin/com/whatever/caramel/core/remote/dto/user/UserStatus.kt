package com.whatever.caramel.core.remote.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserStatus {
    @SerialName("NEW") NEW,
    @SerialName("SINGLE") SINGLE,
    @SerialName("COUPLED") COUPLED,
    ;
}