package com.whatever.caramel.feature.copule.invite.clipboard

import androidx.compose.ui.platform.ClipEntry

/**
 * 플랫폼별 Clipboard에 사용되는 ClipEntry 생성 로직
 * @author GunHyung Ham
 * @since 2025.03.26
 */
expect fun createPlatformClipEntry(inviteCode: String): ClipEntry
