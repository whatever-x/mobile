package com.whatever.caramel.core.domain.model.content

/**
 * 서버 응답을 받기위한 용도, 계층간 데이터 전달을 위해서 만들어진 모델
 * */
data class ContentMetadata(
    val contentId: Long,
    val contentType: String,
)