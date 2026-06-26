package com.whatever.caramel.feature.copule.invite.config

interface InviteUrlProvider {
    fun createInviteUrl(inviteCode: String): String
}

internal fun createInviteUrl(
    inviteBaseUrl: String,
    inviteCode: String,
): String {
    val separator = if (inviteBaseUrl.contains("?")) "&" else "?"
    return "$inviteBaseUrl${separator}p1=$inviteCode"
}
