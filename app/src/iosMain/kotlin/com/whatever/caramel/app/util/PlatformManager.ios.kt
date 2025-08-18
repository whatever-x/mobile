package com.whatever.caramel.app.util

import com.whatever.caramel.core.domain.vo.app.Platform
import platform.Foundation.NSBundle

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformManager : Platform {
    actual override val appPlatform: Platform.AppPlatform
        get() = Platform.AppPlatform.IOS

    actual override val versionCode: Int
        get() = NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? Int ?: 0

    actual override val storeUri: String
        get() = "https://apps.apple.com/kr/app/%EC%B9%B4%EB%9D%BC%EB%A9%9C-caramel-%EC%9A%B0%EB%A6%AC%EB%A7%8C%EC%9D%98-%EA%B0%90%EC%84%B1-%EC%BB%A4%ED%94%8C-%EB%8B%A4%EC%9D%B4%EC%96%B4%EB%A6%AC/id6745321351"
}