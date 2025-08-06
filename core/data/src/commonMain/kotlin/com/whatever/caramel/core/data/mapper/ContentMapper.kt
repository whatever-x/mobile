package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.LinkMetaData
import com.whatever.caramel.core.remote.dto.common.OgTagDto
import com.whatever.caramel.core.remote.dto.tag.TagDetailResponse

internal fun TagDetailResponse.toTag(): Tag =
    Tag(
        id = id,
        label = label,
    )

internal fun List<TagDetailResponse>.toTagList(): List<Tag> = map { it.toTag() }

internal fun OgTagDto.toLinkMetaData(): LinkMetaData =
    LinkMetaData(
        url = this.url,
        title = this.title,
        imageUrl = this.image
    )