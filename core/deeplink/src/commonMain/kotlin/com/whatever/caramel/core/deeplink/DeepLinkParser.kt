package com.whatever.caramel.core.deeplink

data class ParsedUri(
    val scheme: String?,
    val host: String?,
    val pathSegments: List<String>,
    val queryParams: Map<String, String>
)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object DeepLinkParser {

    fun parse(uri: String): ParsedUri

}