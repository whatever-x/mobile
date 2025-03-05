package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.data.database.entity.SampleDetailEntity
import com.whatever.caramel.core.data.database.entity.SampleEntity
import com.whatever.caramel.core.data.database.entity.SampleEntityWithDetail
import com.whatever.caramel.core.data.remote.dto.response.SampleDetailDto
import com.whatever.caramel.core.data.remote.dto.response.SampleGetMethodResponseDto
import com.whatever.caramel.core.domain.model.SampleDetail
import com.whatever.caramel.core.domain.model.SampleModel

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