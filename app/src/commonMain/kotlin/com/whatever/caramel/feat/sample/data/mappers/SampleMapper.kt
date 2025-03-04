package com.whatever.caramel.feat.sample.data.mappers

import com.whatever.caramel.feat.sample.data.database.SampleDetailEntity
import com.whatever.caramel.feat.sample.data.database.SampleEntity
import com.whatever.caramel.feat.sample.data.database.SampleEntityWithDetail
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

fun SampleModel.toSampleEntity(): SampleEntity = SampleEntity(
    name = this.name,
    localDateTime = this.localDateTime,
)

fun SampleDetail.toSampleDetailEntity(parentName : String) : SampleDetailEntity = SampleDetailEntity(
    sampleName = parentName,
    description = this.description
)

fun SampleEntityWithDetail.toSampleModel() : SampleModel =
    SampleModel(
        name = this.sample.name,
        localDateTime = this.sample.localDateTime,
        detail = this.detailArray[0].toSampleDetail(),
        detailArray = this.detailArray.map { it.toSampleDetail() }
    )

fun SampleDetailEntity.toSampleDetail() : SampleDetail = SampleDetail(
    description = this.description
)