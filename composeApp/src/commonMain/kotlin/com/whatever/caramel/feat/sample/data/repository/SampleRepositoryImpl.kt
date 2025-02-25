package com.whatever.caramel.feat.sample.data.repository

import com.whatever.caramel.feat.sample.data.database.SampleDao
import com.whatever.caramel.feat.sample.data.local.LocalSampleDataSource
import com.whatever.caramel.feat.sample.data.mappers.toSampleDetailEntity
import com.whatever.caramel.feat.sample.data.mappers.toSampleEntity
import com.whatever.caramel.feat.sample.data.mappers.toSampleModel
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSource
import com.whatever.caramel.feat.sample.domain.SampleModel
import com.whatever.caramel.feat.sample.domain.SampleRepository

class SampleRepositoryImpl(
    private val remoteSampleDataSource: RemoteSampleDataSource,
    private val localSampleDataSource: LocalSampleDataSource,
) : SampleRepository {

    override suspend fun getSampleData(): SampleModel =
        remoteSampleDataSource.getSampleData().toSampleModel()

    override suspend fun saveSampleDataToLocal(data: SampleModel) {
        localSampleDataSource.saveSampleData(
            data.toSampleEntity(),
            data.detailArray.map { it.toSampleDetailEntity(data.name) }
        )
    }

    override suspend fun getSampleDataFromLocal(): List<SampleModel> =
        localSampleDataSource.getSampleData().map { it.toSampleModel() }

    override suspend fun getSampleNameFromLocal(): String = localSampleDataSource.getSampleName()

    override suspend fun saveSampleNameToLocal(name: String) {
        localSampleDataSource.setSampleName(name)
    }
}