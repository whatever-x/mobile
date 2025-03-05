package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.database.dao.SampleDao
import com.whatever.caramel.core.data.datastore.SampleLocalDataSource
import com.whatever.caramel.core.data.mapper.toSampleDetailEntity
import com.whatever.caramel.core.data.mapper.toSampleEntity
import com.whatever.caramel.core.data.mapper.toSampleModel
import com.whatever.caramel.core.data.remote.datasource.RemoteSampleDataSource
import com.whatever.caramel.core.domain.model.SampleModel
import com.whatever.caramel.core.domain.repository.SampleRepository

class SampleRepositoryImpl(
    private val remoteSampleDataSource: RemoteSampleDataSource,
    private val localSampleDataSource: SampleLocalDataSource,
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

    override suspend fun getSampleNameFromLocal(): String = localSampleDataSource.getSampleName()

    override suspend fun saveSampleNameToLocal(name: String) {
        localSampleDataSource.setSampleName(name)
    }
}