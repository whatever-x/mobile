package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.content.ContentInfo

data class Content(
    val id: Long,
    val contentInfo: ContentInfo
) {
    // 현재는 Content에 강하게 연관이 있지만, 추가 생성 등 작업을 한다면 분리 필요
    data class Tag(
        val id: Long,
        val label: String,
    )
}


