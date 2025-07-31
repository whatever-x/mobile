package com.whatever.caramel.core.domain.entity

import com.whatever.caramel.core.domain.vo.content.ContentInfo
import kotlinx.datetime.LocalDate

/**
 * Content는 스케쥴이 될 수도 있지만 메모의 역할도 하고 있음
 * Memo로 표기, ContentInfo로 내부를 분리하고 싶지만 서버와의 소통을 위해 Content로 표시
 * */
data class Content(
    val id: Long,
    val contentInfo: ContentInfo,
    val createdAt: LocalDate    // TODO : 서버에서 Cotnent에만 제공해줌
)