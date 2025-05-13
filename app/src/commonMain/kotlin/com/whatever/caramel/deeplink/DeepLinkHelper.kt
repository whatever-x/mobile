package com.whatever.caramel.deeplink

data class ParsedUri(
    val path: String?,
    val queryParams: Map<String, String?>
)

object DeepLinkHelper {

    private const val INVITE_CODE_KEY = "invite_code"
    private val INVITATION_PATH = setOf("7nAT", "invitation")

    private fun parseUri(uri: String): ParsedUri {
        val urlParts = uri.split("?", limit = 2)
        val base = urlParts.getOrNull(0)?.substringAfter("://")?.trimEnd('/')
        val path = base?.split("/")?.getOrNull(1)
        val query = urlParts.getOrNull(1)

        val queryParams = query?.split("&")
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
            in INVITATION_PATH -> {
                parsed.queryParams[INVITE_CODE_KEY]?.let {
                    CaramelDeepLink.InviteCode(it)
                } ?: CaramelDeepLink.Unknown
            }

            else -> CaramelDeepLink.Unknown
        }
    }

}