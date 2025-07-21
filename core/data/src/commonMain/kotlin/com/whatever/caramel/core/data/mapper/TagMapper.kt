package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse

internal fun TagDetailResponse.toTag(): Tag =
    Tag(
        id = id,
        label = label,
    )

internal fun List<TagDetailResponse>.toTags(): List<Tag> = map { it.toTag() }
