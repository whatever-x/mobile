package com.whatever.caramel.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalAppDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : LocalAppDataSource {

    private val _activityParticipationFlow = dataStore.data.map {
        it[activityParticipationCountKey] ?: 0
    }

    private val _appLaunchCountFlow = dataStore.data.map {
        it[appLaunchCountKey] ?: 0
    }
    override val activityParticipationCountFlow: Flow<Int>
        get() = _activityParticipationFlow

    override val appLaunchCountFlow: Flow<Int>
        get() = _appLaunchCountFlow

    override suspend fun saveActivityParticipationCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[activityParticipationCountKey] = count
        }
    }

    override suspend fun saveAppLaunchCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[appLaunchCountKey] = count
        }
    }

    override suspend fun saveInAppReviewRequestDate(dateTime: String) {
        dataStore.edit { prefs ->
            prefs[inAppReviewRequestDateKey] = dateTime
        }
    }

    override suspend fun fetchInAppReviewRequestDate() =
        dataStore.data.first().let { prefs ->
            prefs[inAppReviewRequestDateKey] ?: ""
        }

    companion object {
        private const val PREFS_KEY_APP_LAUNCH_COUNT = "prefs_key_app_launch_count"
        private const val PREFS_KEY_ACTIVITY_PARTICIPATION_COUNT = "prefs_key_activity_participation_count"
        private const val PREFS_KEY_IN_APP_REVIEW_REQUEST_DATE =
            "prefs_key_in_app_review_request_date"
        private val appLaunchCountKey by lazy { intPreferencesKey(PREFS_KEY_APP_LAUNCH_COUNT) }
        private val inAppReviewRequestDateKey by lazy {
            stringPreferencesKey(
                PREFS_KEY_IN_APP_REVIEW_REQUEST_DATE,
            )
        }
        private val activityParticipationCountKey by lazy {
            intPreferencesKey(
                PREFS_KEY_ACTIVITY_PARTICIPATION_COUNT,
            )
        }
    }
}
