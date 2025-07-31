package com.whatever.caramel.core.domain_change.repository

import com.whatever.caramel.core.domain.vo.common.LinkMetaData

interface LinkMetadataRepository {
    suspend fun getLinkMetadata(url: String): LinkMetaData?
}
