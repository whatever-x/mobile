package com.whatever.caramel.feature.login.social

import androidx.compose.runtime.Composable

sealed interface SocialAuthResult<out T> {
    data class Success<T>(
        val data: T,
    ) : SocialAuthResult<T>

    data object UserCancelled : SocialAuthResult<Nothing>

    data object Error : SocialAuthResult<Nothing>
}

interface SocialAuthProvider<T> {
    @Composable
    fun get(): SocialAuthenticator<T>
}

interface SocialAuthenticator<T> {
    suspend fun authenticate(): SocialAuthResult<T>
}
