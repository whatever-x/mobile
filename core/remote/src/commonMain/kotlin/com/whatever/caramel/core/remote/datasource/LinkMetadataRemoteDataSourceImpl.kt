package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.common.OgTagDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlin.text.RegexOption

class LinkMetadataRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : LinkMetadataRemoteDataSource {
    override suspend fun fetchLinkMetadata(url: String): OgTagDto? {
        val htmlContent = fetchHtmlContent(url)

        val ogTitle = htmlContent?.let { parseMetaTagContent(it, "og:title") }
        val ogImage = htmlContent?.let { parseMetaTagContent(it, "og:image") }
        val ogUrl = htmlContent?.let { parseMetaTagContent(it, "og:url") }

        val finalTitle = ogTitle ?: extractTitleFromUrl(url)

        return finalTitle?.let { title ->
            OgTagDto(
                url = ogUrl ?: url,
                title = title,
                image = ogImage ?: ""
            )
        }
    }

    private suspend fun fetchHtmlContent(url: String): String? = runCatching {
        httpClient.get(url).bodyAsText()
    }.onFailure { e ->
        println("Error fetching HTML for $url: ${e.message}")
    }.getOrNull()

    private fun parseMetaTagContent(htmlContent: String, propertyValue: String): String? {
        val regex = Regex(
            """<meta\s+property\s*=\s*["']${Regex.escape(propertyValue)}["']\s+content\s*=\s*["']([^"']*)["']\s*/?>""",
            RegexOption.IGNORE_CASE
        )
        return regex.find(htmlContent)?.groups?.get(1)?.value?.trim()?.takeIf { it.isNotBlank() }
    }

    private fun extractTitleFromUrl(url: String): String? {
        return runCatching {
            val authorityAndPath = url.substringAfter("://", missingDelimiterValue = "")
            if (authorityAndPath.isBlank()) {
                return@runCatching null
            }

            val pathSegment = authorityAndPath.substringAfter("/", missingDelimiterValue = "")

            val textToProcess = if (pathSegment.isNotEmpty() && pathSegment != authorityAndPath) {
                pathSegment.split('/').lastOrNull()?.takeIf { it.isNotBlank() }
            } else {
                authorityAndPath.split('/').firstOrNull()?.takeIf { it.isNotBlank() }
            }

            textToProcess
                ?.replace("-", " ")
                ?.replace("_", " ")
                ?.capitalizeWords()
        }.getOrNull()
    }

    private fun String.capitalizeWords(): String =
        this.split(' ')
            .filter { it.isNotBlank() }
            .joinToString(" ") { word ->
                word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
} 