package com.whatever.caramel.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first

class CoupleDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : CoupleDataSource {
    override suspend fun fetchCoupleId(): Long {
        return dataStore.data.first().let { prefs ->
            prefs[coupleIdKey] ?: 0L
        }
    }

    override suspend fun saveCoupleId(coupleId: Long) {
        dataStore.edit { prefs ->
            prefs[coupleIdKey] = coupleId
        }
    }

    companion object {
        private const val PREFS_KEY_COUPLE_ID = "coupleId"

        private val coupleIdKey by lazy { longPreferencesKey(PREFS_KEY_COUPLE_ID) }
    }
}