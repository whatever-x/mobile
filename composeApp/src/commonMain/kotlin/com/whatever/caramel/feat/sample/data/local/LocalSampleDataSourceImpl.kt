package com.whatever.caramel.feat.sample.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.whatever.caramel.feat.sample.data.database.SampleDao
import com.whatever.caramel.feat.sample.data.database.SampleDetailEntity
import com.whatever.caramel.feat.sample.data.database.SampleEntity
import com.whatever.caramel.feat.sample.data.database.SampleEntityWithDetail
import kotlinx.coroutines.flow.first

class LocalSampleDataSourceImpl(
    private val prefs: DataStore<Preferences>,
    private val sampleDao: SampleDao
) : LocalSampleDataSource {
    override suspend fun getSampleName(): String {
        prefs.data.first().let {
            val key = stringPreferencesKey(PREFS_KEY_NAME)
            return it[key] ?: ""
        }
    }

    override suspend fun setSampleName(name: String) {
        prefs.edit { datastore ->
            val key = stringPreferencesKey(PREFS_KEY_NAME)
            datastore[key] = name
        }
    }

    override suspend fun saveSampleData(
        sampleEntity: SampleEntity,
        sampleDetailEntities: List<SampleDetailEntity>
    ) {
        sampleDao.insertSampleWithDetail(
            sampleEntity = sampleEntity,
            sampleDetailEntity = sampleDetailEntities
        )
    }

    override suspend fun getSampleData(): List<SampleEntityWithDetail> = sampleDao.getAllSamples()

    companion object {
        private const val PREFS_KEY_NAME = "name"
    }
}