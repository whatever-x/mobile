package com.whatever.caramel.core.remote.dto.memo

import kotlinx.serialization.SerialName

enum class ContentAssigneeDto {
    @SerialName("ME")
    ME,

    @SerialName("PARTNER")
    PARTNER,

    @SerialName("US")
    US,
}
