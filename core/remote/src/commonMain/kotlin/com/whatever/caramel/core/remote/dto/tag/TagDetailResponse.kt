package com.whatever.caramel.core.remote.dto.tag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagDataResponse(
    @SerialName("tagList")
    val tags: List<TagDetailResponse>,
)

@Serializable
data class TagDetailResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("label")
    val label: String,
)
