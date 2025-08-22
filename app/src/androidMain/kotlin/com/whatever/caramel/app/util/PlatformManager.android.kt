package com.whatever.caramel.app.util

import com.whatever.caramel.app.BuildConfig
import com.whatever.caramel.core.domain.vo.app.Platform

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformManager : Platform {

    actual override val appPlatform: Platform.AppPlatform
        get() = Platform.AppPlatform.ANDROID

    actual override val versionCode: Int
        get() = BuildConfig.VERSION_CODE

}
