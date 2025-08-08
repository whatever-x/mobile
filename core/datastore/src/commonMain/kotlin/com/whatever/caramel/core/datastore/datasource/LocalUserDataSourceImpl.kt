package com.whatever.caramel.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class LocalUserDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : LocalUserDataSource {
    override suspend fun fetchUserStatus(): String =
        dataStore.data.first().let { preferences ->
            preferences[userStatusKey] ?: ""
        }

    override suspend fun saveUserStatus(state: String) {
        dataStore.edit { prefs ->
            prefs[userStatusKey] = state
        }
    }

    override suspend fun deleteUserStatus() {
        dataStore.edit { prefs ->
            prefs[userStatusKey] = ""
        }
    }

    companion object {
        private const val PREFS_KEY_USER_STATUS = "userStatus"
        private val userStatusKey by lazy { stringPreferencesKey(PREFS_KEY_USER_STATUS) }
    }
}
