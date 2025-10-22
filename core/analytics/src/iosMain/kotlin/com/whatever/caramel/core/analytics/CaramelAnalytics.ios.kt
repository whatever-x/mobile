package com.whatever.caramel.core.analytics

import firebaseAnalyticsBridge.FBAParam
import firebaseAnalyticsBridge.FirebaseAnalyticsBridge
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class CaramelAnalyticsImpl : CaramelAnalytics {

    private val firebaseAnalyticsBridge = FirebaseAnalyticsBridge()

    override fun logEvent(
        eventName: String,
        params: Map<String, Any>?,
    ) {
        val pairs = params?.toFBAParams()
        firebaseAnalyticsBridge.logEventWithName(
            name = eventName,
            paramPairs = pairs
        )
    }

    private fun Map<String, Any?>.toFBAParams(): List<FBAParam> {
        val list = mutableListOf<FBAParam>()
        this.forEach { (k, v) -> list += FBAParam(key = k, value = v) }

        return list
    }

}
