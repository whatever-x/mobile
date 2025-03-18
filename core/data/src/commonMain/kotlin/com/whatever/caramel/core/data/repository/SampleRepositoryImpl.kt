package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.database.dao.SampleDao
import com.whatever.caramel.core.datastore.SampleDatastore
import com.whatever.caramel.core.data.mapper.toSampleDetailEntity
import com.whatever.caramel.core.data.mapper.toSampleEntity
import com.whatever.caramel.core.data.mapper.toSampleModel
import com.whatever.caramel.core.domain.model.SampleModel
import com.whatever.caramel.core.domain.repository.SampleRepository
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource

class SampleRepositoryImpl(
    private val remoteSampleDataSource: RemoteAuthDataSource,
    private val sampleDatastore: SampleDatastore,
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

    override suspend fun getSampleNameFromLocal(): String = sampleDatastore.getSampleName()

    override suspend fun saveSampleNameToLocal(name: String) {
        sampleDatastore.setSampleName(name)
    }
}