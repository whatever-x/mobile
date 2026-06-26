package com.whatever.caramel.feature.copule.invite.config

import platform.Foundation.NSBundle

class IosInviteUrlProvider : InviteUrlProvider {
    private val inviteBaseUrl =
        NSBundle.mainBundle.objectForInfoDictionaryKey(INVITE_ONELINK_URL_KEY) as? String
            ?: error("Missing '$INVITE_ONELINK_URL_KEY' in Info.plist.")

    override fun createInviteUrl(inviteCode: String): String =
        createInviteUrl(
            inviteBaseUrl = inviteBaseUrl,
            inviteCode = inviteCode,
        )

    private companion object {
        const val INVITE_ONELINK_URL_KEY = "InviteOneLinkUrl"
    }
}
