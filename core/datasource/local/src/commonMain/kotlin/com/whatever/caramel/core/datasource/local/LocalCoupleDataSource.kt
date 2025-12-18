package com.whatever.caramel.core.datasource.local

interface LocalCoupleDataSource {
    suspend fun fetchCoupleId(): Long

    suspend fun saveCoupleId(coupleId: Long)

    suspend fun deleteCoupleId()
}
