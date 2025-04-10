package com.whatever.caramel.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class TokenDataSourceImpl(
    private val dataStore : DataStore<Preferences>
) : TokenDataSource {
    
    override suspend fun createToken(
        accessToken: String,
        refreshToken: String
    ) {
        dataStore.edit { prefs ->
            prefs[accessTokenKey] = accessToken
            prefs[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun fetchAccessToken(): String {
        return dataStore.data.first().let { prefs ->
            prefs[accessTokenKey] ?: ""
        }
    }

    override suspend fun fetchRefreshToken(): String {
        return dataStore.data.first().let { prefs ->
            prefs[refreshTokenKey] ?: ""
        }
    }

    companion object {
        private const val PREFS_KEY_ACCESS_TOKEN = "accessToken"
        private const val PREFS_KEY_REFRESH_TOKEN = "refreshToken"

        private val accessTokenKey by lazy { stringPreferencesKey(PREFS_KEY_ACCESS_TOKEN) }
        private val refreshTokenKey by lazy { stringPreferencesKey(PREFS_KEY_REFRESH_TOKEN) }
    }
}