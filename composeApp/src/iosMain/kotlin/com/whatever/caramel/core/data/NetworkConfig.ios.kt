package com.whatever.caramel.core.data

import com.whatever.caramel.core.domain.CaramelException
import com.whatever.caramel.core.domain.ErrorUiType
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