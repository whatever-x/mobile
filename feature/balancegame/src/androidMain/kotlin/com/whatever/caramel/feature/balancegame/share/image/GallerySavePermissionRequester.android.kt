package com.whatever.caramel.feature.balancegame.share.image

import android.os.Build

internal actual fun requiresLegacyGalleryWritePermission(): Boolean = Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
