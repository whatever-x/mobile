package com.whatever.caramel.core.domain_change.repository

import com.whatever.caramel.core.domain.entity.Content
import com.whatever.caramel.core.domain.params.content.MemoEditParameter
import com.whatever.caramel.core.domain.model.content.ContentMetadata
import com.whatever.caramel.core.domain.params.content.MemoParameter
import com.whatever.caramel.core.domain.model.content.ContentWithCursor

interface ContentRepository {
    suspend fun createContent(parameter: MemoParameter): ContentMetadata

    suspend fun getContents(
        size: Int?,
        cursor: String?,
        sortType: String?,
        tagId: Long?,
    ): ContentWithCursor

    suspend fun updateContent(
        contentId: Long,
        parameter: MemoEditParameter,
    )

    suspend fun deleteContent(contentId: Long)

    suspend fun getContent(contentId: Long): Content

    suspend fun getTags(): List<Tag>
}
