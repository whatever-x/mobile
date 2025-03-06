package com.whatever.caramel.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class SampleDatastoreImpl(
    private val prefs : DataStore<Preferences>
) : SampleDatastore {
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

    companion object {
        private const val PREFS_KEY_NAME = "name"
    }
}