package com.whatever.caramel.feat.sample.data.repository

import com.whatever.caramel.feat.sample.data.database.SampleDao
import com.whatever.caramel.feat.sample.data.mappers.toSampleDetailEntity
import com.whatever.caramel.feat.sample.data.mappers.toSampleEntity
import com.whatever.caramel.feat.sample.data.mappers.toSampleModel
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSource
import com.whatever.caramel.feat.sample.domain.SampleModel
import com.whatever.caramel.feat.sample.domain.SampleRepository

class SampleRepositoryImpl(
    private val remoteSampleDataSource: RemoteSampleDataSource,
    private val sampleDao: SampleDao
) : SampleRepository {

    override suspend fun getSampleData(): SampleModel =
        remoteSampleDataSource.getSampleData().toSampleModel()

    override suspend fun saveSampleDataToLocal(data: SampleModel) {
        sampleDao.insertSampleWithDetail(
            data.toSampleEntity(),
            data.detailArray.map { it.toSampleDetailEntity(data.name) }
        )
    }

    override suspend fun getSampleDataFromLocal(): List<SampleModel> =
        sampleDao.getAllSamples().map { it.toSampleModel() }
}