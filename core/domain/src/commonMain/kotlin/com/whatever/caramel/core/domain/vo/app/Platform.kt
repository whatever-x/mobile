package com.whatever.caramel.core.domain.vo.app

interface Platform {
    enum class AppPlatform {
        ANDROID,
        IOS,
    }

    val appPlatform: AppPlatform

    val versionCode: Int
}
