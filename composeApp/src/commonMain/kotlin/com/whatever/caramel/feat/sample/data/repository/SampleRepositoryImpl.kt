package com.whatever.caramel.feat.sample.data.repository

import com.whatever.caramel.feat.sample.data.mappers.toSampleModel
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSource
import com.whatever.caramel.feat.sample.domain.SampleModel
import com.whatever.caramel.feat.sample.domain.SampleRepository

class SampleRepositoryImpl(
    private val remoteSampleDataSource: RemoteSampleDataSource
) : SampleRepository {

    override suspend fun getSampleData(): SampleModel =
        remoteSampleDataSource.getSampleData().toSampleModel()

}