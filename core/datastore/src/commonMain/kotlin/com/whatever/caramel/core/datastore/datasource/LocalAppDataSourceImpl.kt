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
    private val _balanceGameParticipationCountFlow: Flow<Int> =
        dataStore.data.map { prefs ->
            prefs[balanceGameParticipationCountKey] ?: 0
        }
    override val balanceGameParticipationCountFlow: Flow<Int>
        get() = _balanceGameParticipationCountFlow

    private val _contentCreateCountFlow: Flow<Int> =
        dataStore.data.map { prefs ->
            prefs[contentCreateCountKey] ?: 0
        }
    override val contentCreateCountFlow: Flow<Int>
        get() = _contentCreateCountFlow

    override suspend fun saveAppLaunchCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[appLaunchCountKey] = count
        }
    }

    override suspend fun fetchAppLaunchCount(): Int =
        dataStore.data.first().let { prefs ->
            prefs[appLaunchCountKey] ?: 0
        }

    override suspend fun saveBalanceGameParticipationCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[balanceGameParticipationCountKey] = count
        }
    }

    override suspend fun saveContentCreateCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[contentCreateCountKey] = count
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
        private const val PREFS_KEY_CONTENT_CREATE_COUNT = "prefs_key_content_create_count"
        private const val PREFS_KEY_BALANCE_GAME_PARTICIPATION_COUNT =
            "prefs_key_balance_game_participation_count"
        private const val PREFS_KEY_IN_APP_REVIEW_REQUEST_DATE =
            "prefs_key_in_app_review_request_date"

        private val appLaunchCountKey by lazy { intPreferencesKey(PREFS_KEY_APP_LAUNCH_COUNT) }
        private val contentCreateCountKey by lazy {
            intPreferencesKey(
                PREFS_KEY_CONTENT_CREATE_COUNT,
            )
        }
        private val balanceGameParticipationCountKey by lazy {
            intPreferencesKey(
                PREFS_KEY_BALANCE_GAME_PARTICIPATION_COUNT,
            )
        }
        private val inAppReviewRequestDateKey by lazy {
            stringPreferencesKey(
                PREFS_KEY_IN_APP_REVIEW_REQUEST_DATE,
            )
        }
    }
}
