package com.whatever.caramel.core.domain.usecase.common

import com.whatever.caramel.core.domain.repository.LinkMetadataRepository
import com.whatever.caramel.core.domain.vo.common.LinkMetaData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.flowOf

class GetLinkPreviewsForContentUseCase(
    private val linkMetadataRepository: LinkMetadataRepository
) {
    private val urlRegex = Regex("""(https?://\S+)""")

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(content: String?): Flow<List<LinkMetaData>> {
        val distinctUrls = content.takeIf { !it.isNullOrBlank() }
            ?.let { nonEmptyContent ->
                urlRegex.findAll(nonEmptyContent)
                    .map { it.value }
                    .toList()
                    .distinct()
                    .takeIf { it.isNotEmpty() }
            }

        return if (distinctUrls.isNullOrEmpty()) {
            flowOf(emptyList())
        } else {
            flow {
                val linkPreviews = distinctUrls.asFlow()
                    .flatMapMerge { url ->
                        flow { emit(linkMetadataRepository.getLinkMetadata(url)) }
                    }
                    .filterNotNull()
                    .toList()
                emit(linkPreviews)
            }
        }
    }
}