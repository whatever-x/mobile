package com.whatever.caramel.core.datasource.remote.dto.memo.request

import com.whatever.caramel.core.datasource.remote.dto.memo.ContentAssigneeDto
import com.whatever.caramel.core.datasource.remote.dto.tag.TagRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMemoRequest(
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("tags")
    val tags: List<TagRequest>?,
    @SerialName("contentAssignee")
    val contentAssignee: ContentAssigneeDto,
)
