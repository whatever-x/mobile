package com.whatever.caramel.external.deeplink

import com.whatever.caramel.external.deeplink.model.AppsFlyerDeepLinkParameter
import com.whatever.caramel.external.deeplink.model.AppsFlyerDeepLinkValue
import com.whatever.caramel.external.deeplink.model.CaramelDeepLink
import kotlinx.coroutines.flow.Flow

interface DeepLinkHandler {
    val deepLinkFlow: Flow<CaramelDeepLink>
    val deepLinkData: Pair<AppsFlyerDeepLinkValue, List<String>>?

    val isRunningApp: Boolean

    fun handleAppsFlyerData(
        deepLinkValue: String,
        params: Map<AppsFlyerDeepLinkParameter, String?>,
    )

    fun handleAppsFlyerDataRaw(
        deepLinkValue: String,
        rawParams: Map<String, String?>,
    )

    fun clearDeepLinkData()

    fun runningApp()
}
