package com.whatever.caramel.external.deeplink.model

sealed interface CaramelDeepLink {
    data class Invite(
        val code: String,
    ) : CaramelDeepLink

    data object Unknown : CaramelDeepLink
}
