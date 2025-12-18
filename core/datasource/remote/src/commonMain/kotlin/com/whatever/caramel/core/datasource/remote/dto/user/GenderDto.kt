package com.whatever.caramel.core.datasource.remote.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GenderDto {
    @SerialName("MALE")
    MALE,

    @SerialName("FEMALE")
    FEMALE,
}
