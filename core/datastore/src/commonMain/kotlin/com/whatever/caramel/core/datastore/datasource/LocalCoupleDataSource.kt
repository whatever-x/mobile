package com.whatever.caramel.core.datastore.datasource

interface LocalCoupleDataSource {
    suspend fun fetchCoupleId(): Long

    suspend fun saveCoupleId(coupleId: Long)

    suspend fun deleteCoupleId()
}
