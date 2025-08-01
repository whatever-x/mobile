package com.whatever.caramel.core.remote.dto.memo.response

import com.whatever.caramel.core.remote.dto.memo.ContentAssigneeDto
import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CursoredContentResponse(
    @SerialName("list") val list: List<MemoResponse>,
    @SerialName("cursor") val cursor: Cursor,
)

@Serializable
data class Cursor(
    @SerialName("next") val next: String?,
)

@Serializable
data class MemoResponse(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String?,
    @SerialName("description") val description: String?,
    @SerialName("isCompleted") val isCompleted: Boolean,
    @SerialName("tagList") val tagList: List<TagDetailResponse>,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("contentAssignee") val contentAssignee: ContentAssigneeDto,
)
