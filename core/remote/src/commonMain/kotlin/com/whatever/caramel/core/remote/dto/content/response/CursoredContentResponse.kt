package com.whatever.caramel.core.remote.dto.content.response

import com.whatever.caramel.core.remote.dto.tag.TagDataResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CursoredContentResponse(
    @SerialName("list") val list : List<ContentResponse>,
    @SerialName("cursor") val cursor : Cursor
)

@Serializable
data class Cursor(
    @SerialName("next") val next: String
)

@Serializable
data class ContentResponse(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("isCompleted") val isCompleted: Boolean,
    @SerialName("tagList") val tagList: List<TagDataResponse>,
    @SerialName("createdAt") val createdAt: String,
)
