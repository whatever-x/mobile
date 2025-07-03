package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Tag

interface TagRepository {
    suspend fun getTags(): List<Tag>
}
