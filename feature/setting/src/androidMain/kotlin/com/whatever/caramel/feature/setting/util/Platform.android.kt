@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.whatever.caramel.feature.setting.util

import com.whatever.caramel.feature.setting.BuildKonfig

actual object Platform {
    actual val versionName: String
        get() = BuildKonfig.VERSION_NAME
}
