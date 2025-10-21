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

    private val _balanceGameParticipationCountFlow: Flow<Int> = dataStore.data.map { prefs ->
        prefs[balanceGameParticipationCountKey] ?: 0
    }

    private val _memoCreateCountFlow: Flow<Int> = dataStore.data.map { prefs ->
        prefs[memoCreateCountKey] ?: 0
    }

    private val _scheduleCreateCountFlow: Flow<Int> = dataStore.data.map { prefs ->
        prefs[scheduleCreateCountKey] ?: 0
    }

    override suspend fun saveAppLaunchCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[appLaunchCountKey] = count
        }
    }

    override suspend fun fetchAppLaunchCount(): Int =
        dataStore.data.first().let { prefs ->
            prefs[appLaunchCountKey] ?: 1
        }

    override suspend fun saveBalanceGameParticipationCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[balanceGameParticipationCountKey] = count
        }
    }

    override suspend fun fetchBalanceGameParticipationCount() = _balanceGameParticipationCountFlow

    override suspend fun saveMemoCreateCount(count: Int) {
        dataStore.data.first().let { prefs ->
            prefs[memoCreateCountKey] ?: 0
        }
    }

    override suspend fun fetchMemoCreateCount() = _memoCreateCountFlow

    override suspend fun saveScheduleCreateCount(count: Int) {
        dataStore.data.first().let { prefs ->
            prefs[scheduleCreateCountKey] ?: 0
        }
    }

    override suspend fun fetchScheduleCreateCount() = _scheduleCreateCountFlow

    override suspend fun saveInAppReviewRequestDate(dateTime: String) {
        dataStore.edit { prefs ->
            prefs[inAppReviewRequestDateKey] = dateTime
        }
    }

    override suspend fun fetchInAppReviewRequestDate() = dataStore.data.first().let { prefs ->
        prefs[inAppReviewRequestDateKey] ?: ""
    }

    companion object {
        private const val PREFS_KEY_APP_LAUNCH_COUNT = "prefs_key_app_launch_count"
        private const val PREFS_KEY_SCHEDULE_CREATE_COUNT = "prefs_key_schedule_create_count"
        private const val PREFS_KEY_MEMO_CREATE_COUNT = "prefs_key_memo_create_count"
        private const val PREFS_KEY_BALANCE_GAME_PARTICIPATION_COUNT =
            "prefs_key_balance_game_participation_count"
        private const val PREFS_KEY_IN_APP_REVIEW_REQUEST_DATE =
            "prefs_key_in_app_review_request_date"

        private val appLaunchCountKey by lazy { intPreferencesKey(PREFS_KEY_APP_LAUNCH_COUNT) }
        private val scheduleCreateCountKey by lazy {
            intPreferencesKey(
                PREFS_KEY_SCHEDULE_CREATE_COUNT
            )
        }
        private val memoCreateCountKey by lazy { intPreferencesKey(PREFS_KEY_MEMO_CREATE_COUNT) }
        private val balanceGameParticipationCountKey by lazy {
            intPreferencesKey(
                PREFS_KEY_BALANCE_GAME_PARTICIPATION_COUNT
            )
        }
        private val inAppReviewRequestDateKey by lazy {
            stringPreferencesKey(
                PREFS_KEY_IN_APP_REVIEW_REQUEST_DATE
            )
        }
    }
}
