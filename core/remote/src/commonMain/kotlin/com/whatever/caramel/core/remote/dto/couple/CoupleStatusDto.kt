package com.whatever.caramel.core.remote.dto.couple

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CoupleStatusDto {
    @SerialName("ACTIVE") ACTIVE,
    @SerialName("INACTIVE") INACTIVE,
    ;
}
