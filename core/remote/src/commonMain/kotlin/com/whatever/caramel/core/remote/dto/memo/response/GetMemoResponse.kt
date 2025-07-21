package com.whatever.caramel.core.remote.dto.memo.response

import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetMemoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val tagList: List<TagDetailResponse>,
    val createdAt: String,
)
