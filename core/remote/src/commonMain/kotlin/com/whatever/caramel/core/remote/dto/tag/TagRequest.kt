package com.whatever.caramel.core.remote.dto.tag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagRequest(
    @SerialName("tagId")
    val id: Long,
)