package com.whatver.caramel.feature.setting.util

import platform.Foundation.NSBundle

actual object Platform {
    val versionName: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as String
}
