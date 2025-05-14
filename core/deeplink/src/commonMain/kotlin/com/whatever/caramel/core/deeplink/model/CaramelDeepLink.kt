package com.whatever.caramel.core.deeplink.model

sealed interface CaramelDeepLink {

    data class Invite(val code: String) : CaramelDeepLink

    data object Unknown : CaramelDeepLink

}
