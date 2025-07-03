package com.whatever.caramel.feature.copule.invite.clipboard

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

@OptIn(ExperimentalComposeUiApi::class)
actual fun createPlatformClipEntry(inviteCode: String): ClipEntry = ClipEntry.withPlainText(inviteCode)
