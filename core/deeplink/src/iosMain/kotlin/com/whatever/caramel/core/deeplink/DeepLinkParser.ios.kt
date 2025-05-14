package com.whatever.caramel.core.deeplink

import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLQueryItem

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DeepLinkParser {

    actual fun parse(uri: String): ParsedUri {
        val nsUrl = NSURLComponents.componentsWithString(uri) ?: return ParsedUri(null, null, emptyList(), emptyMap())

        val scheme = nsUrl.scheme
        val host = nsUrl.host
        val pathSegments = nsUrl.path?.split("/")?.filter { it.isNotBlank() } ?: emptyList()
        val queryParams = nsUrl.queryItems?.filterIsInstance<NSURLQueryItem>()?.associate { it.name to (it.value ?: "") } ?: emptyMap()

        return ParsedUri(
            scheme = scheme,
            host = host,
            pathSegments = pathSegments,
            queryParams = queryParams
        )
    }

}