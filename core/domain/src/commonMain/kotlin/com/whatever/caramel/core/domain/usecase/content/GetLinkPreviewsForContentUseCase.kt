package com.whatever.caramel.core.domain.usecase.content

import com.whatever.caramel.core.domain.policy.ContentPolicy
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.vo.content.LinkMetaData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

class GetLinkPreviewsForContentUseCase(
    private val contentRepository: ContentRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(content: String?): Flow<List<LinkMetaData>> {
        val distinctUrls =
            content
                .takeIf { !it.isNullOrBlank() }
                ?.let { nonEmptyContent ->
                    ContentPolicy.URL_PATTERN
                        .findAll(nonEmptyContent)
                        .map { it.value }
                        .toList()
                        .distinct()
                        .takeIf { it.isNotEmpty() }
                }

        return if (distinctUrls.isNullOrEmpty()) {
            flowOf(emptyList())
        } else {
            flow {
                val linkPreviews =
                    distinctUrls
                        .asFlow()
                        .flatMapMerge { url ->
                            flow { emit(contentRepository.getLinkMetadata(url)) }
                        }.filterNotNull()
                        .toList()
                emit(linkPreviews)
            }
        }
    }
}