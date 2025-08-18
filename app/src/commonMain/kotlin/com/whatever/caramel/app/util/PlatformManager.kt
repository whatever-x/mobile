package com.whatever.caramel.app.util

import com.whatever.caramel.core.domain.vo.app.Platform

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PlatformManager : Platform {
    override val appPlatform: Platform.AppPlatform

    override val versionCode: Int

    override val storeUri: String
}