package com.whatever.caramel.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class UserDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : UserDataSource {
    override suspend fun getOnBoardingCompletion(): Boolean {
        return dataStore.data.first().let { preferences ->
            preferences[onboardingCompletionKey] ?: false
        }
    }

    override suspend fun setOnBoardingCompletion(completed: Boolean) {
        dataStore.edit { prefs ->
            prefs[onboardingCompletionKey] = completed
        }
    }

    override suspend fun getUserStatus(): String {
        return dataStore.data.first().let { preferences ->
            preferences[userStatusKey] ?: ""
        }
    }

    override suspend fun setUserStatus(state: String) {
        dataStore.edit { prefs ->
            prefs[userStatusKey] = state
        }
    }

    companion object {
        private const val PREFS_KEY_ONBOARDING_COMPLETION = "onboardingCompletion"
        private const val PREFS_KEY_USER_STATUS = "userStatus"

        private val onboardingCompletionKey by lazy {
            booleanPreferencesKey(
                PREFS_KEY_ONBOARDING_COMPLETION
            )
        }
        private val userStatusKey by lazy { stringPreferencesKey(PREFS_KEY_USER_STATUS) }
    }
}