package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse

internal fun TagDetailResponse.toTag(): Tag {
    return Tag(
        id = id,
        label = label,
    )
}

internal fun List<TagDetailResponse>.toTags(): List<Tag> {
    return map { it.toTag() }
}
