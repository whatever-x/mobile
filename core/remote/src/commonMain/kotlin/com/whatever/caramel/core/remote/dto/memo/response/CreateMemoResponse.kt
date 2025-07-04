package com.whatever.caramel.core.remote.dto.memo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMemoResponse(
    @SerialName("contentId")
    val contentId: Long,
    @SerialName("contentType")
    val contentType: String,
)
