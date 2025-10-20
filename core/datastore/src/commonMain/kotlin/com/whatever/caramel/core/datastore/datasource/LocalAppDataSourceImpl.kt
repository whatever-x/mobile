package com.whatever.caramel.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class LocalAppDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : LocalAppDataSource {
    override suspend fun saveReviewRequestDate(date: String) {
        dataStore.edit { prefs ->
            prefs[reviewRequestDateKey] = date
        }
    }

    override suspend fun fetchReviewRequestDate(): String = dataStore.data.first().let { prefs ->
        prefs[reviewRequestDateKey] ?: ""
    }

    override suspend fun saveAppLaunchCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[appLaunchCountKey] = count
        }
    }

    override suspend fun fetchAppLaunchCount(): Int = dataStore.data.first().let { prefs ->
        prefs[appLaunchCountKey] ?: 1
    }

    companion object {
        private const val PREFS_KEY_REVIEW_REQUEST_DATE = "prefs_key_review_request_date"
        private const val PREFS_KEY_APP_LAUNCH_COUNT = "prefs_key_app_launch_count"

        private val reviewRequestDateKey by lazy {
            stringPreferencesKey(
                PREFS_KEY_REVIEW_REQUEST_DATE
            )
        }
        private val appLaunchCountKey by lazy { intPreferencesKey(PREFS_KEY_APP_LAUNCH_COUNT) }
    }

}