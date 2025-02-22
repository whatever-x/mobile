package com.whatever.caramel.feat.sample.data.mappers

import com.whatever.caramel.feat.sample.data.dto.response.SampleDetailDto
import com.whatever.caramel.feat.sample.data.dto.response.SampleGetMethodResponseDto
import com.whatever.caramel.feat.sample.domain.SampleDetail
import com.whatever.caramel.feat.sample.domain.SampleModel

fun SampleGetMethodResponseDto.toSampleModel(): SampleModel =
    SampleModel(
        name = this.name,
        localDateTime = this.localDateTime,
        detail = this.detail.toSampleDetail(),
        detailArray = this.detailArray.map { it.toSampleDetail() }
    )

fun SampleDetailDto.toSampleDetail(): SampleDetail =
    SampleDetail(description = this.description)