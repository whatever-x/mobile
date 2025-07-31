package com.whatever.caramel.core.domain_change.model.content

import com.whatever.caramel.core.domain.entity.Content

/**
 * UI 모델이지만 계층간 전달을 위해서 생성
 * Map으로 변경 가능
 * 혹은 가독성을 위해서 클래스로 관리
 * 건형씨는 VO로 설정
 * */
data class ContentWithCursor(
    val nextCursor: String?,
    val memos: List<Content>,
)