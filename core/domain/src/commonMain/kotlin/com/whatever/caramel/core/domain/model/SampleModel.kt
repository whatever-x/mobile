package com.whatever.caramel.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SampleModel(
    val name: String = "",
    val localDateTime: String = "",
    val detail: SampleDetail = SampleDetail(),
    val detailArray: List<SampleDetail> = emptyList(),
)

@Serializable
data class SampleDetail(
    val description: String = ""
)