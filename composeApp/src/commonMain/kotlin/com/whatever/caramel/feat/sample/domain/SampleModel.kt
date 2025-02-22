package com.whatever.caramel.feat.sample.domain

data class SampleModel(
    val name: String = "",
    val localDateTime: String = "",
    val detail: SampleDetail = SampleDetail(),
    val detailArray: List<SampleDetail> = emptyList(),
)

data class SampleDetail(
    val description: String = ""
)