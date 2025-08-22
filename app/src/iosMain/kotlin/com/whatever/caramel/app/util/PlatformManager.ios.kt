package com.whatever.caramel.app.util

import com.whatever.caramel.core.domain.vo.app.Platform
import platform.Foundation.NSBundle

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformManager : Platform {

    actual override val appPlatform: Platform.AppPlatform
        get() = Platform.AppPlatform.IOS

    actual override val versionCode: Int
        get() = (NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String)?.toIntOrNull() ?: 0

}
