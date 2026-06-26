package com.whatever.caramel.feature.copule.invite.config

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class AndroidInviteUrlProvider(
    context: Context,
) : InviteUrlProvider {
    private val inviteBaseUrl = context.getInviteOneLinkUrl()

    override fun createInviteUrl(inviteCode: String): String =
        createInviteUrl(
            inviteBaseUrl = inviteBaseUrl,
            inviteCode = inviteCode,
        )

    private fun Context.getInviteOneLinkUrl(): String {
        val applicationInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()),
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            }

        return applicationInfo.metaData?.getString(INVITE_ONELINK_URL_METADATA_NAME)
            ?: error("Missing '$INVITE_ONELINK_URL_METADATA_NAME' in AndroidManifest metadata.")
    }

    private companion object {
        const val INVITE_ONELINK_URL_METADATA_NAME = "com.whatever.caramel.INVITE_ONELINK_URL"
    }
}
