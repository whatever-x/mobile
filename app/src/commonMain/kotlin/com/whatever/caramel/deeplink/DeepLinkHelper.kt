package com.whatever.caramel.deeplink

data class ParsedUri(
    val path: String?,
    val queryParams: Map<String, String?>
)

object DeepLinkHelper {

    private fun parseUri(uri: String): ParsedUri {
        val urlParts = uri.split("?", limit = 2)
        val base = urlParts.getOrNull(0)?.substringAfter("://")?.trimEnd('/')
        val path = base?.substringAfter("/")?.lowercase()
        val query = urlParts.getOrNull(1)

        val queryParams = query
            ?.split("&")
            ?.mapNotNull {
                val (key, value) = it.split("=", limit = 2).let { parts ->
                    parts.getOrNull(0) to parts.getOrNull(1)
                }
                key?.let { k -> k to value }
            }
            ?.toMap() ?: emptyMap()

        return ParsedUri(
            path = path,
            queryParams = queryParams
        )
    }

    fun parseCaramelDeepLink(uri: String): CaramelDeepLink {
        val parsed = parseUri(uri)

        return when (parsed.path) {
            "invite", "invitation" -> {
                parsed.queryParams["invite_code"]?.let {
                    CaramelDeepLink.InviteCode(it)
                } ?: CaramelDeepLink.Unknown
            }

            else -> CaramelDeepLink.Unknown
        }
    }

}