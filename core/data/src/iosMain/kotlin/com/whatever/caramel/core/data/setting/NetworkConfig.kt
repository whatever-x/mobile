package com.whatever.caramel.core.data.setting

import platform.Foundation.NSBundle
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual object NetworkConfig {
    actual val BASE_URL: String = NSBundle.mainBundle.objectForInfoDictionaryKey("CaramelBaseUrl") as? String
            ?: throw CaramelException(
                message = "BaseUrl Error",
                debugMessage = "BaseUrl Error",
                errorUiType = ErrorUiType.EMPTY_UI
            )
    actual val isDebug: Boolean = Platform.isDebugBinary
}