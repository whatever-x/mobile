package com.whatever.caramel.core.deeplink

import androidx.core.net.toUri

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DeepLinkParser {

    actual fun parse(uri: String): ParsedUri {
        val parsed = uri.toUri()
        val pathSegments = parsed.pathSegments ?: emptyList()
        val queryParams = parsed.queryParameterNames.associateWith { parsed.getQueryParameter(it) ?: "" }

        return ParsedUri(
            scheme = parsed.scheme,
            host = parsed.host,
            pathSegments = pathSegments,
            queryParams = queryParams
        )
    }

}