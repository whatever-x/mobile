package com.whatever.caramel.feature.setting.util

import com.whatever.caramel.feature.setting.BuildConfig

actual object Platform {
    actual val versionName: String
        get() = BuildConfig.VERSION_NAME
}