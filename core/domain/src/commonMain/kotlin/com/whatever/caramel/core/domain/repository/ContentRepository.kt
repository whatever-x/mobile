package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.LinkMetaData

interface ContentRepository {
    suspend fun getLinkMetadata(url: String): LinkMetaData?

    suspend fun getTagList(): List<Tag>
}
