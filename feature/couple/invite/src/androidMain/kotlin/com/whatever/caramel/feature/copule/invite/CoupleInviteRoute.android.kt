package com.whatever.caramel.feature.copule.invite

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual fun createPlatformClipEntry(inviteCode: String): ClipEntry =
    ClipEntry(
        clipData = ClipData.newPlainText("invite code",inviteCode)
    )
