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

    override suspend fun fetchToken(): Pair<String?, String?> {
        dataStore.data.first().let { prefs ->
            val accessToken = prefs[accessTokenKey]
            val refreshToken = prefs[refreshTokenKey]

            return Pair(
                first = accessToken,
                second = refreshToken
            )
        }
    }

    override suspend fun fetchRefreshToken(): String? {
        dataStore.data.first().let { prefs ->
            val refreshToken = prefs[refreshTokenKey]

            return refreshToken
        }
    }

    companion object {
        private const val PREFS_KEY_ACCESS_TOKEN = "accessToken"
        private const val PREFS_KEY_REFRESH_TOKEN = "refreshToken"

        private val accessTokenKey by lazy { stringPreferencesKey(PREFS_KEY_ACCESS_TOKEN) }
        private val refreshTokenKey by lazy { stringPreferencesKey(PREFS_KEY_REFRESH_TOKEN) }
    }
}