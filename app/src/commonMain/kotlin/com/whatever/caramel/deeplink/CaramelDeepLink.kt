package com.whatever.caramel.deeplink

sealed interface CaramelDeepLink {

    data class InviteCode(val code: String) : CaramelDeepLink

    data object Unknown : CaramelDeepLink

}