package com.whatever.caramel.feature.setting.util

import platform.Foundation.NSBundle

actual object Platform {
    actual val versionName: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as String
}
