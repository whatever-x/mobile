package com.whatever.caramel.core.deeplink

import com.whatever.caramel.core.deeplink.model.AppsFlyerDeepLinkParameter
import com.whatever.caramel.core.deeplink.model.AppsFlyerDeepLinkValue
import com.whatever.caramel.core.deeplink.model.CaramelDeepLink
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class DeepLinkHandlerImpl : DeepLinkHandler {
    private val _deepLinkFlow = Channel<CaramelDeepLink>(capacity = Channel.CONFLATED)
    override val deepLinkFlow: Flow<CaramelDeepLink> = _deepLinkFlow.receiveAsFlow()

    private var _isRunningApp: Boolean = false
    override val isRunningApp
        get() = _isRunningApp

    private var _deepLinkData: Pair<AppsFlyerDeepLinkValue, List<String>>? = null
    override val deepLinkData
        get() = _deepLinkData

    override fun handleAppsFlyerData(
        deepLinkValue: String,
        params: Map<AppsFlyerDeepLinkParameter, String?>,
    ) {
        val deepLinkType = AppsFlyerDeepLinkValue.entries.find { it.deepLinkValue == deepLinkValue }

        when (deepLinkType) {
            AppsFlyerDeepLinkValue.INVITE -> {
                val inviteCode = params[AppsFlyerDeepLinkParameter.Parameter1] ?: return

                if (isRunningApp) {
                    _deepLinkFlow.trySend(CaramelDeepLink.Invite(code = inviteCode))
                } else {
                    _isRunningApp = true
                    _deepLinkData = deepLinkType to listOf(inviteCode)
                }
            }

            null -> {
                Napier.d { "찾을수 없는 딥링크 타입입니다. $deepLinkValue" }
            }
        }
    }

    override fun handleAppsFlyerDataRaw(
        deepLinkValue: String,
        rawParams: Map<String, String?>,
    ) {
        val mappedParams =
            AppsFlyerDeepLinkParameter.entries.associateWith { param ->
                rawParams[param.parameterName]
            }

        handleAppsFlyerData(deepLinkValue, mappedParams)
    }

    override fun clearDeepLinkData() {
        _deepLinkData = null
    }

    override fun runningApp() {
        _isRunningApp = true
    }
}
